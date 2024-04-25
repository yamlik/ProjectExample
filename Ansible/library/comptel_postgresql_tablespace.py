#!/usr/bin/python
# -*- coding: utf-8 -*-

ANSIBLE_METADATA = {
    'metadata_version': '1.0',
    'supported_by': 'TEST',
    'status': ['stableinterface']
}

DOCUMENTATION = '''
---
module: TEST_postgresql_tablespace
short_description: Add or remove PostgreSQL tablespace from a remote host.
description:
   - Add or remove PostgreSQL tablespace from a remote host.
version_added: "2.2"
author: TEST Corporation
options:
  name:
    description:
      - name of the tablespace to add or remove
    required: true
    type: string
  location:
    description:
      - location of the tablespace to add or remove
      - module doesn't support location change for existing tablespace
      - required only when creating tablespace
    required: true
    type: string
  database:
    description:
      - name of the database for remote connection
    required: false
    type: string
    default: postgres
  login_user:
    description:
      - The username used for remote connection
    required: false
    type: string
    default: postgres
  login_password:
    description:
      - The password used for remote connection
    required: false
    type: string
  login_host:
    description:
      - Host running the database
    required: false
    type: string
    default: localhost
  owner:
    description:
      - The name of the user who will own the tablespace
    required: false
    type: string
  port:
    description:
      - Database port to connect to.
    required: false
    type: string
    default: 5432
  state:
    description:
      - The tablespace state
    required: false
    type: string
    default: present
    choices: [ "present", "absent" ]
notes:
   - This Ansible module use psycopg2, a Python PostgreSQL database adapter. Module psycopg2 should be installed on 
   target machine. For Ubuntu-based systems, postgresql client, libpq-dev, and python-psycopg2 packages need to be 
   installed.
requirements: [ psycopg2 ]
'''

EXAMPLES = '''
- name: Create new tablespace with name CTL
  TEST_postgresql_tablespace: 
    name: CTL
    location: /var/lib/pgsql/ctl

- name: Create new tablespace with name CTL and owner elink
  TEST_postgresql_tablespace: 
    name: CTL
    location: /var/lib/pgsql/ctl
    owner: elink
    
- name: Remove tablespace CTL
  TEST_postgresql_tablespace: 
    name: CTL
    state: absent
'''

RETURN = '''
tablespace:
    description: Name of the tablespace
    returned: success, changed
    type: string
    sample: "CTL"
'''


try:
    import psycopg2
    import psycopg2.extras
except ImportError:
    postgresqldb_found = False
else:
    postgresqldb_found = True

class NotSupportedError(Exception):
    pass


# ===========================================
# PostgreSQL module specific support methods.
#

def set_owner(cursor, tablespace, owner):
    query = "ALTER TABLESPACE %s OWNER TO %s" % (
            pg_quote_identifier(tablespace, 'database'),
            pg_quote_identifier(owner, 'role'))
    cursor.execute(query)
    return True

def get_tablespace_info(cursor, tablespace):
    query = """
    SELECT spcname as name, pg_catalog.pg_get_userbyid(spcowner) as owner, pg_catalog.pg_tablespace_location(oid) as location
    FROM pg_catalog.pg_tablespace
    WHERE spcname = %(tablespace)s
    """
    cursor.execute(query, {'tablespace': tablespace})
    return cursor.fetchone()

def tablespace_exists(cursor, tablespace):
    query = "SELECT spcname FROM pg_catalog.pg_tablespace WHERE spcname = %(tablespace)s"
    cursor.execute(query, {'tablespace': tablespace})
    return cursor.rowcount == 1

def tablespace_delete(cursor, tablespace):
    if tablespace_exists(cursor, tablespace):
        query = "DROP TABLESPACE %s" % pg_quote_identifier(tablespace, 'tablespace')
        cursor.execute(query)
        return True
    else:
        return False

def tablespace_create(cursor, tablespace, location, owner):
    if not tablespace_exists(cursor, tablespace):
        query_fragments = ['CREATE TABLESPACE %s' % pg_quote_identifier(tablespace, 'database')]
        if owner:
            query_fragments.append('OWNER %s' % pg_quote_identifier(owner, 'role'))
        if location:
            query_fragments.append("LOCATION '%s'" % location)
        query = ' '.join(query_fragments)
        cursor.execute(query)
        return True
    else:
        tablespace_info = get_tablespace_info(cursor, tablespace)
        if owner and owner != tablespace_info['owner']:
            return set_owner(cursor, tablespace, owner)
        else:
            return False

def tablespace_matches(cursor, tablespace, location, owner):
    if not tablespace_exists(cursor, tablespace):
        return False
    else:
        tablespace_info = get_tablespace_info(cursor, tablespace)
        if owner and owner != tablespace_info['owner']:
            return False
        return True

def validate_parameter( key, value, required, default):
    if value == "":
        if required == True:
            raise ValueError("Required parameter value missing: " + key + ". Value: " + value)
        else:
            value = default

    return value

# ===========================================
# Module execution.
#

def main():
    module = AnsibleModule(
        argument_spec=dict(
            login_user=dict(default="postgres"),
            login_password=dict(default=""),
            login_host=dict(default="localhost"),
            port=dict(default="5432"),
            tablespace=dict(required=True, aliases=['name']),
            location=dict(),
            owner=dict(default=""),
            database=dict(default="postgres"),
            state=dict(default="present", choices=["absent", "present"]),
        ),
        supports_check_mode = True
    )

    if not postgresqldb_found:
        module.fail_json(msg="the python psycopg2 module is required")

    tablespace = module.params["tablespace"]
    owner = module.params["owner"]
    state = module.params["state"]
    database = module.params["database"]
    location = module.params["location"]
    changed = False

    try:
        if state == 'present':
            validate_parameter("location", location, True, "")
    except ValueError as v:
        module.fail_json(changed=False, msg="Parameter validation failed: " + str(v))

    # To use defaults values, keyword arguments must be absent, so
    # check which values are empty and don't include in the **kw
    # dictionary
    params_map = {
        "login_host":"host",
        "login_user":"user",
        "login_password":"password",
        "port":"port"
    }
    kw = dict( (params_map[k], v) for (k, v) in module.params.iteritems()
              if k in params_map and v != '' )

    try:
        db_connection = psycopg2.connect(database=database, **kw)
        # Enable autocommit so we can create databases
        if psycopg2.__version__ >= '2.4.2':
            db_connection.autocommit = True
        else:
            db_connection.set_isolation_level(psycopg2
                                              .extensions
                                              .ISOLATION_LEVEL_AUTOCOMMIT)
        cursor = db_connection.cursor(
                cursor_factory=psycopg2.extras.DictCursor)
    except Exception:
        e = get_exception()
        module.fail_json(msg="unable to connect to database: %s" %( str(e) ))

    try:
        if module.check_mode:
            if state == "absent":
                changed = not tablespace_exists(cursor, tablespace)
            elif state == "present":
                changed = not tablespace_matches(cursor, tablespace, location, owner)
            module.exit_json(changed=changed, tablespace=tablespace)

        if state == "absent":
            try:
                changed = tablespace_delete(cursor, tablespace)
            except SQLParseError:
                e = get_exception()
                module.fail_json(msg=str(e))

        elif state == "present":
            try:
                changed = tablespace_create(cursor, tablespace, location, owner)
            except SQLParseError:
                e = get_exception()
                module.fail_json(msg=str(e))
    except NotSupportedError:
        e = get_exception()
        module.fail_json(msg=str(e))
    except SystemExit:
        # Avoid catching this on Python 2.4
        raise
    except Exception:
        e = get_exception()
        module.fail_json(msg="Database query failed: %s" %( str(e) ))

    module.exit_json(changed=changed, tablespace=tablespace)

# import module snippets
from ansible.module_utils.basic import *
from ansible.module_utils.database import *
if __name__ == '__main__':
    main()

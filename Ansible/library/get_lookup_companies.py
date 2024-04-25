#!/usr/bin/python

ANSIBLE_METADATA = {
    "metadata_version": "1.0",
    "supported_by": "TEST",
    "status": ["stableinterface"]
}

DOCUMENTATION = '''
---
module: get_lookup_companies
short_description: Returns a list of lookup company IDs (COMPANYID) from LOOKUP_COMPANIES database table
options:
  db
    description:
      - Name of the database
  db_user
    description:
      - Database user to query the database
  db_password
    description:
      - Password of the database user
  db_host
    description:
      - Hostname of the database server
  db_port
    description:
      - Port number of the database server
'''

EXAMPLES = '''
- name: "SELECT COMPANYID FROM LOOKUP_COMPANIES"
  register: result
  get_lookup_companies:
    db: "TEST"
    db_host: "{{ db_el_host }}"
    db_port: 5432
    db_user: "{{ db_el_user }}"
    db_password: "{{ db_el_password }}"
'''

RETURN = '''
companyIds:
    description: a list of lookup company IDs
    returned: success
    type: list
'''

import psycopg2

def connect_db(db, db_user, db_password, db_host, db_port):
    try:
        return psycopg2.connect(dbname = db, user = db_user, password = db_password, host = db_host, port = db_port)
    except Exception:
        e = get_exception()
        module.fail_json(msg = "Unable to connect to database: %s" %(str(e)))

def get_lookup_companies(db_conn):
    cursor = db_conn.cursor()
    cursor.execute("SELECT companyid FROM lookup_companies")
    result = []
    for columns in cursor.fetchall():
        result.append(columns[0])
    cursor.close()
    return result

def main():

    module_args = dict(
        db = dict(required = True, type = "str"),
        db_user = dict(required = True, type = "str"),
        db_password = dict(required = True, type = "str"),
        db_host = dict(required = True, type = "str"),
        db_port = dict(required = True, type = "str")
    )

    module = AnsibleModule(
        argument_spec = module_args
    )

    db_conn = connect_db(
        module.params["db"],
        module.params["db_user"],
        module.params["db_password"],
        module.params["db_host"],
        module.params["db_port"]
    )

    companies = get_lookup_companies(db_conn)
    db_conn.close()

    result = dict(
        changed = False,
        companyIds = companies
    )

    module.exit_json(**result)

from ansible.module_utils.basic import AnsibleModule

if __name__ ==  "__main__":
    main()


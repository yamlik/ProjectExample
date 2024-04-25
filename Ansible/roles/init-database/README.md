init-database
=============

Creates a PostgreSQL user, database and runs changelogs given as parameters.
Should be run via Role Dependencies.

Requirements
------------

Requires pip and yum. Requires Ansible 2.2 or newer.

Role Variables
--------------

Default variables:

    login_db:       postgres  # Database where to login for the first time
    login_port:     5432      # Port that the PostgreSQL server is listening
    db_encoding:    DEFAULT   # Character encoding
    db_lc_collate:  DEFAULT   # String sort order by locale
    db_lc_ctype:    DEFAULT   # Character classification by locale
    db_tablespace:  DEFAULT   # Name of tablespace

All the following variables need to be set by the user:

    login_user      # User with which to login the first time
    login_password  # Password for that user
    login_host      # Hostname of the PostgreSQL server
    db_name         # Name of the database to create
    db_jdbc_url     # JDBC url to the database in the format: jdbc:postgresql://<Fully-Qualified-Domain-Name>:<Port>/<db_name>
    user_name       # Username for the created user.
    user_password   # Password for the created user.
    user_role_attrs # Role attributes for the created user.
    changelogs_dir  # Directory where changelogs will be copied.
    changelogs      # A list of changelog files for liquibase in the format: [ "<filename>", "<another-filename>"].
                    # NOTE: files under my-role/files/ can be referred to by filename only,
                    # they will be automatically discovered by this role, given that my-role
                    # has a Role Dependency to init-database

Dependencies
------------

No dependencies.

Example Playbook
----------------

    - hosts: servers
      roles:
         - {  role: init-database,
              login_user: postgres,
              login_password: postgresPass,
              login_host: db.service.consul,
              db_name: mydb,
              db_jdbc_url: jdbc:postgresql://db.service.consul:5432/mydb,
              user_name: myuser,
              user_password: myuserPass,
              user_role_attrs: CREATEDB,LOGIN,
              changelogs_dir: /opt/TEST/mywebapp,
              changelogs: ['createMyTables.xml', 'createMoreTables.xml']
           }

License
-------

Commercial

Author Information
------------------

TEST Corporation, Platform Components <Platform_Components@TEST.com>

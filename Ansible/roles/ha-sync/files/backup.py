#!/usr/bin/env python
# -*- coding: utf-8 -*-

import subprocess
import tarfile


def main():
    subprocess.check_call(['mkdir', '-p', '/appdata/ha-sync'])
    with tarfile.open('/appdata/ha-sync/backup.tar.gz', 'w:gz') as f:
        for p in [
                '/etc/confd/conf.d/ha-sync', '/etc/confd/templates/ha-sync',
                '/etc/ha-sync', '/var/log/ha-sync'
        ]:
            f.add(p)


if __name__ == '__main__':
    main()

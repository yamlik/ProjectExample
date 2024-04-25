#!/usr/bin/env python
# -*- coding: utf-8 -*-

import tarfile


def main():
    with tarfile.open('/appdata/ha-sync/backup.tar.gz') as f:
        f.extractall('/')


if __name__ == '__main__':
    main()

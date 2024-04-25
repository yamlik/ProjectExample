#!/usr/bin/env python
# -*- coding: utf-8 -*-

import subprocess


def main():
    subprocess.check_call(['rm', '-f', '/appdata/ha-sync/backup.tar.gz'])


if __name__ == '__main__':
    main()

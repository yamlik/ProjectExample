#!/usr/bin/env python
# -*- coding: utf-8 -*-

import json

import click


@click.command()
@click.option(
    '-p',
    '--policy_file',
    default='/tmp/BR/policy/dynamic.policy',
    help='policy file path')
@click.argument('backups', nargs=-1)
def main(policy_file, backups):
    "add backup-ed files to policy_file"
    with open(policy_file) as f:
        policies = json.load(f)

    for f in backups:
        policies['Policy']['HostIP']['backuplist'].append(f)

    with open(policy_file, 'w') as f:
        json.dump(policies, f, indent=4)


if __name__ == '__main__':
    main()

import os

import writeValues
import sshActionWrapper
import cbamApiClass

@given(u'values exist in context')
def step_impl(context):
   context.feature.testing = "True"

@given(u'value set in previous step exists')
def step_impl(context):
    assert(context.feature.testing == "True" )

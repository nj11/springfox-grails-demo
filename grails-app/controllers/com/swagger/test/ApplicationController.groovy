package com.swagger.test

import grails.core.GrailsApplication
import grails.plugins.*
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}

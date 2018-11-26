package com.swagger.test

import grails.converters.JSON
import grails.rest.*
import groovy.json.JsonSlurper


import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

//For Swagger UI
//http://localhost:8080/swagger-ui.html

class TodoController extends RestfulController {
    static responseFormats = ['json']
    TodoController() {
        super(Todo)
    }

    // maps to /todo GET ( for list all todo objects )
    // maps to /todo?max=5 GET
    @Override
    @ApiOperation("Display all todo objects")
    def index(){
        List<Todo> todos = new ArrayList()
        def max = params.max
        if(max)
            todos = Todo.list(max:new Integer(max).intValue())
        else
            todos = Todo.list()

        respond todos

    }//End index

    //maps to /todo POST ( for create new todo object )
    @Override
    def save(){
        def list = new JsonSlurper().parseText(request.getJSON().toString())
        list.each{
            def title = it.title
            def completed = it.isComplete
            Todo todo = new Todo();
            todo.title = title
            todo.isComplete = completed
            todo.save()
            render todo as JSON
        }

    }

    //maps to /todo/1 GET ( for find by id )
    @Override
    @ApiOperation(value = "Search for TODO by id")
    @ApiResponses(value = [ @ApiResponse(code = 404, message = "#{domainClass.name} not found") ])
    def show(){
        def id = params.id
        Todo todo = Todo.findById(new Long(id))
        respond todo
    }

    //maps to /todo/1 DELETE ( for deletes )
    @Override
    def delete(){
        def id  = params.id
        Todo todo = Todo.findById(new Long(id))
        todo.delete(flush:true)
        render "Successfully deleted Todo with id ${id}"
    }

    @Override
    //maps to /todo/1 PUT ( for updates )
    //PUT mandatory to send all parameters for updates ( even non changed values )
    def update(){
        def id  = params.id
        Todo todo = Todo.findById(new Long(id))
        def jsonObj = request.getJSON()
        if(jsonObj.title)  todo.title = request.getJSON().title
        if(jsonObj.isComplete)  todo.title = request.getJSON().isComplete
        todo.save(flush:true)
        respond todo
    }

    @Override
    //maps to /todo/1 PATCH ( for partial updates )
    //PATCH just send data that you need to update - so PATCH requires less bandwidth
    def patch(){
        update()
    }



}

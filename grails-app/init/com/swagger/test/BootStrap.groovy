package com.swagger.test

class BootStrap {

    def init = { servletContext ->
        for(int i = 0; i < 10;i++){
            Todo todo = new Todo()
            todo.setId(new Long(i))
            todo.setTitle("Title:" + i)
            todo.setIsComplete(false)
            todo.save()
        }
    }
    def destroy = {
    }
}

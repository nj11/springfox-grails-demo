package com.swagger.test

import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])

class Todo {
    Long id
    String title

    Long getId() {
        return id
    }

    boolean isComplete

    void setId(Long id) {
        this.id = id
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    boolean getIsComplete() {
        return isComplete
    }

    void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete
    }

}
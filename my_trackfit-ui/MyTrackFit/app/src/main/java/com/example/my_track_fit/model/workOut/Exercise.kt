package com.example.my_track_fit.model.workOut

class Exercise (
    //Attributes / constructor
    val id: Int = 0, // Data base assigns automatically the id
    private var name: String,
    private var workOut: WorkOut
) {
    //Getters & setters
    //---name
    fun setName(name: String){
        this.name = name
    }
    fun getName(): String {
        return name
    }
    //---id
    fun getId(): Int {
        return id
    }
    //---workOut
    fun setWorkOut(workOut: WorkOut){
        this.workOut = workOut
    }
    fun getWorkOut(): WorkOut {
        return workOut
    }
}
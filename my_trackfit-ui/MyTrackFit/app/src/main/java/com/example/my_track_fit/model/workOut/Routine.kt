package com.example.my_track_fit.model.workOut

class Routine (
    //Attributes / constructor
    //val id: Int = 0, // Data base assigns automatically the id
    private var name: String,
    private var blockList: MutableList<Block>,
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
    //---workOut
    fun setWorkOut(workOut: WorkOut){
        this.workOut = workOut
    }
    fun getWorkOut(): WorkOut {
        return workOut
    }
    //---blockList
    fun setBlockList(blockList: MutableList<Block>){
        this.blockList = blockList
    }
    fun getBlockList(): MutableList<Block> {
        return blockList
    }

    //methods
    fun addBlock(blockName: String) {
        // Crear un nuevo objeto Block
        val newBlock = Block(
            name = blockName, // Asignar el nombre pasado por parámetro
            weeks = mutableListOf(), // Inicializar la lista de semanas vacía
            routine = this // Asignar la rutina actual
        )
        // Agregar el nuevo bloque a la lista de bloques
        blockList.add(newBlock)
    }
    fun deleteBlock(block: Block) {
        blockList.remove(block)
    }
}
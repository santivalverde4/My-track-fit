package com.example.my_track_fit.model.workOut

abstract class WorkOut(
    //Attributes / constructor
    val id: Int = 0, // Data base assigns automatically the id
    private var routineList: MutableList<Routine>,
    private var exerciseList: MutableList<Exercise>
) {
    //Methods
    fun addRoutine(routineName: String) {
        val newRoutine = Routine(
            name = routineName,
            blockList = mutableListOf(),
            workOut = this
        )
        routineList.add(newRoutine)
    }
    fun deleteRoutine(routine: Routine) {
        routineList.remove(routine)
    }

    fun addExercise(exerciseName: String) {
        // Crear un nuevo objeto Exercise
        val newExercise = Exercise(
            name = exerciseName, // Asignar el nombre pasado por parámetro
            workOut = this // Asignar el WorkOut actual
        )
        // Agregar el nuevo Exercise a la lista
        exerciseList.add(newExercise)
    }
    //---deleteExercise
    fun deleteExercise(exercise: Exercise) {
        exerciseList.remove(exercise)
    }

    //Getters & setters
    //---routines
    fun setRoutines(routineList: MutableList<Routine>) {
        this.routineList = routineList
    }
    fun getRoutines(): MutableList<Routine> {
        return routineList
    }
    //---id
    fun getId(): Int {
        return id
    }
    //---Exercise
    fun setExercise(exerciseList: MutableList<Exercise>) {
        this.exerciseList = exerciseList
    }
    fun getExercise(): MutableList<Exercise> {
        return exerciseList
    }
}
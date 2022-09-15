package uz.gita.notesapp.utils

import uz.gita.notesapp.data.models.StateTask

class ExpandableCountryModel {
    companion object{
        const val PARENT = 1
        const val CHILD = 2
    }
    lateinit var countryParent: StateTask.Header
    var type : Int
    lateinit var countryChild : StateTask.Header.Body
    var isExpanded : Boolean
    private var isCloseShown : Boolean


    constructor( type : Int, countryParent: StateTask.Header, isExpanded : Boolean = false,isCloseShown : Boolean = false ){
        this.type = type
        this.countryParent = countryParent
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown

    }


    constructor(type : Int, countryChild : StateTask.Header.Body, isExpanded : Boolean = false,isCloseShown : Boolean = false){
        this.type = type
        this.countryChild = countryChild
        this.isExpanded = isExpanded
        this.isCloseShown = isCloseShown

    }
}
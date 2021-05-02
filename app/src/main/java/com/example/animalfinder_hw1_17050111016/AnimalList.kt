package com.example.animalfinder_hw1_17050111016

import android.util.Log

class AnimalList {
    private val animals: MutableList<Animal> = mutableListOf<Animal>()

    init {
        animals.add(Animal("dog", R.drawable.dog, R.raw.dog))
        animals.add(Animal("cat", R.drawable.cat, R.raw.cat))
        animals.add(Animal("lion", R.drawable.lion, R.raw.lion))
        animals.add(Animal("bear", R.drawable.bear, R.raw.bear))
        animals.add(Animal("duck", R.drawable.duck, R.raw.duck))
        animals.add(Animal("cow", R.drawable.cow, R.raw.cow))
        animals.add(Animal("goat", R.drawable.goat, R.raw.goat))
        animals.add(Animal("horse", R.drawable.horse, R.raw.horse))
        animals.add(Animal("rooster", R.drawable.rooster, R.raw.rooster))
        animals.add(Animal("sheep", R.drawable.sheep, R.raw.sheep))
        animals.add(Animal("rat", R.drawable.rat, R.raw.rat))
        animals.add(Animal("dolphin", R.drawable.dolphin, R.raw.dolphin))
        animals.add(Animal("pig", R.drawable.pig, R.raw.pig))
        animals.add(Animal("bird", R.drawable.bird, R.raw.bird))
        animals.add(Animal("owl", R.drawable.owl, R.raw.owl))
        animals.add(Animal("whale", R.drawable.whale, R.raw.whale))
        animals.add(Animal("wolf", R.drawable.wolf, R.raw.wolf))
        animals.add(Animal("bee", R.drawable.bee, R.raw.bee))
        animals.add(Animal("elephant", R.drawable.elephant, R.raw.elephant))
        animals.add(Animal("snake", R.drawable.snake, R.raw.snake))
    }

    fun getRandomAnimal(count: Int): MutableList<Animal> {
        if(animals.size >= count) {
            val selectedNums: MutableList<Int> = mutableListOf()
            val selectedAnimals: MutableList<Animal> = mutableListOf()
            for (i in 0 until count) {
                var exists = true
                while(exists) {
                    val random = (0 until animals.size).random()
                    if(!selectedNums.contains(random)) {
                        exists = false
                        selectedNums.add(random)
                        selectedAnimals.add(animals[random])
                    }
                }
            }
            return selectedAnimals
        }
        return mutableListOf()
    }

}
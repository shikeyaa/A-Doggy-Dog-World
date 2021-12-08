package com.example.newdoggydogapp.viewmodels

import androidx.lifecycle.*
import com.example.newdoggydogapp.database.DogImageDao
import com.example.newdoggydogapp.database.DogImageEntity
import com.example.newdoggydogapp.network.DogImage
import com.example.newdoggydogapp.network.DogImageApi
import kotlinx.coroutines.launch

class MainViewModel(private val dogImageDao: DogImageDao) : ViewModel() {

    private val _currentlyDisplayedImage = MutableLiveData<DogImage>()
    val currentlyDisplayedImage: LiveData<DogImage> = _currentlyDisplayedImage

    init {
        getNewDog()
    }

    fun getNewDog() {
        viewModelScope.launch {
            // are getting the first item in the list from the response.
            _currentlyDisplayedImage.value = DogImageApi.retrofitService.getRandomDogImage()
        }
    }

    fun addDog(dogImageEntity: DogImageEntity)
    {
        viewModelScope.launch {
            dogImageDao.addDogImage(dogImageEntity)
        }
    }

    fun deleteMostRecentDog(){
        viewModelScope.launch {
            dogImageDao.deleteDog()
        }
    }

    fun getAllDogs(): LiveData<List<DogImageEntity>>
    {
        return dogImageDao.getAllDogImages().asLiveData()
    }
}

class MainViewModelFactory(
    private val dogImageDao: DogImageDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dogImageDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


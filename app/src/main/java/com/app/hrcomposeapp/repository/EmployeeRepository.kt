package com.app.hrcomposeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.hrcomposeapp.database.Employee
import com.app.hrcomposeapp.database.EmployeeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeRepository(private val employeeDao: EmployeeDao) {

    val foundEmployee = MutableLiveData<Employee>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addEmployee(newEmployee: Employee) {
        coroutineScope.launch(Dispatchers.IO) {
            employeeDao.addEmployee(newEmployee)
        }
    }

    fun updateEmployeeDetails(newEmployee: Employee) {
        coroutineScope.launch(Dispatchers.IO) {
            employeeDao.updateEmployeeDetails(newEmployee)
        }
    }

    fun getAllEmployees(): LiveData<List<Employee>> {
        return employeeDao.getAllEmployees()
    }

    fun deleteEmployee(employee: Employee) {
        coroutineScope.launch(Dispatchers.IO) {
            employeeDao.deleteEmployee(employee)
        }
    }

    fun findEmployeeById(empId: String) {
        coroutineScope.launch(Dispatchers.IO) {
            foundEmployee.postValue(employeeDao.findEmployeeById(empId))
        }
    }

}
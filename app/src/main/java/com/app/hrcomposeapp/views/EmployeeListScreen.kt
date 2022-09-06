package com.app.hrcomposeapp.views

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.app.hrcomposeapp.R
import com.app.hrcomposeapp.database.Employee
import com.app.hrcomposeapp.utils.AppScreens
import com.app.hrcomposeapp.utils.CustomToolbar
import com.app.hrcomposeapp.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
) {
    homeViewModel.getAllEmployees()
    Scaffold(
        topBar = {
            CustomToolbar(title = stringResource(id = R.string.app_name))
        },
        content = {
            val employeeList: List<Employee> by homeViewModel.employeeList.observeAsState(initial = listOf())
            if (employeeList.isNotEmpty()) {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                        items(items = employeeList) { emp ->
                            EmployeeCard(employee = emp, navController = navController)
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "Sadly, there are no employees in your company.",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AppScreens.AddEditEmployeeScreen.route + "/" + "0" + "/" + false)
            }, shape = RoundedCornerShape(20.dp)) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            colorResource(id = R.color.black),
                        ),
                        modifier = Modifier
                            .size(25.dp)
                            .clip(RoundedCornerShape(50)),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "Add New Employee",
                    )
                }
            }

        }
    )
}

@Composable
fun EmployeeCard(employee: Employee, navController: NavHostController) {
    val expanded by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                    navController.navigate(
                        AppScreens.EmployeeDetailScreen.routeWithArgs(
                            employee.employeeId.toString()
                        )
                    )
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_person_pin_24),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(
                        colorResource(id = R.color.primaryColor),
                    ),
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50)),
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        text = employee.employeeName,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (expanded) {
                        Text(
                            text = employee.employeeDesignation,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}
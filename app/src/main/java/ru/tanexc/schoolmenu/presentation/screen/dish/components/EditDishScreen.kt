package ru.tanexc.schoolmenu.presentation.screen.dish.components

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.model.Harm
import ru.tanexc.schoolmenu.presentation.MainActivity
import ru.tanexc.schoolmenu.presentation.ui.widgets.SegmentedButtonBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDishScreen(dish: Dish, onClose: () -> Unit, onSubmit: (Dish) -> Unit) {
    val context = LocalContext.current as MainActivity
    val image = remember { mutableStateOf(dish.image) }
    val calories = remember { mutableStateOf(dish.calories.toString()) }
    val protein = remember { mutableStateOf(dish.protein.toString()) }
    val fats = remember { mutableStateOf(dish.fats.toString()) }
    val carbon = remember { mutableStateOf(dish.carbon.toString()) }
    val weight = remember { mutableStateOf(dish.weight.toString()) }
    val harm = remember { mutableStateOf(dish.harm) }
    val cost = remember { mutableStateOf(dish.cost.toString()) }
    val picker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flag)

                val stream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(stream)
                image.value = bitmap.asImageBitmap()
            }
        }

    Scaffold(
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp, 0.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onClose) {
                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(onClick = {
                        onSubmit(
                            dish.copy(
                                image = image.value,
                                calories = calories.value.toFloatOrNull() ?: 0f,
                                protein = protein.value.toFloatOrNull() ?: 0f,
                                fats = fats.value.toFloatOrNull() ?: 0f,
                                carbon = carbon.value.toFloatOrNull() ?: 0f,
                                weight = weight.value.toFloatOrNull() ?: 0f,
                                harm = harm.value,
                                cost = cost.value.toFloatOrNull() ?: 0f
                            )
                        )
                    }) {
                        Text(stringResource(R.string.update))
                    }
                }

            }
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                ) {
                    OutlinedTextField(
                        value = protein.value,
                        onValueChange = {
                            if (it.filter { ch -> !"0123456789.".contains(ch) } == "") {
                                protein.value = it
                                calories.value = "${9 * (fats.value.toFloatOrNull()?: 0f) + 4 * ((carbon.value.toFloatOrNull()?: 0f) + (protein.value.toFloatOrNull()?: 0f))}"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        label = { Text(stringResource(R.string.protein)) },
                        placeholder = { Text(stringResource(R.string.protein)) },
                        suffix = { Text(stringResource(R.string.gr)) }

                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = carbon.value,
                        onValueChange = {
                            if (it.filter { ch -> !"0123456789.".contains(ch) } == "") {
                                carbon.value = it
                                calories.value = "${9 * (fats.value.toFloatOrNull()?: 0f) + 4 * ((carbon.value.toFloatOrNull()?: 0f) + (protein.value.toFloatOrNull()?: 0f))}"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        label = { Text(stringResource(R.string.carbon), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        placeholder = { Text(stringResource(R.string.carbon), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        suffix = { Text(stringResource(R.string.gr)) }
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = fats.value,
                        onValueChange = {
                            if (it.filter { ch -> !"0123456789.".contains(ch) } == "") {
                                fats.value = it
                                calories.value = "${9 * (fats.value.toFloatOrNull()?: 0f) + 4 * ((carbon.value.toFloatOrNull()?: 0f) + (protein.value.toFloatOrNull()?: 0f))}"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        label = { Text(stringResource(R.string.fats), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        placeholder = { Text(stringResource(R.string.fats), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        suffix = { Text(stringResource(R.string.gr)) }
                    )
                }
            }
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                ) {
                    OutlinedTextField(
                        value = calories.value,
                        onValueChange = {
                            if (it.filter { ch -> !"0123456789.".contains(ch) } == "") {
                                calories.value = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        label = { Text(stringResource(R.string.calories), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        placeholder = { Text(stringResource(R.string.calories), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        suffix = { Text(stringResource(R.string.ccal), overflow = TextOverflow.Ellipsis, maxLines = 1) }

                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = weight.value,
                        onValueChange = {
                            if (it.filter { ch -> !"0123456789.".contains(ch) } == "") {
                                weight.value = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        label = { Text(stringResource(R.string.weight), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        placeholder = { Text(stringResource(R.string.weight), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        suffix = { Text(stringResource(R.string.gr)) }

                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = cost.value,
                        onValueChange = {
                            if (it.filter { ch -> !"0123456789.".contains(ch) } == "") {
                                cost.value = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        label = { Text(stringResource(R.string.cost), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        placeholder = { Text(stringResource(R.string.cost), overflow = TextOverflow.Ellipsis, maxLines = 1) },
                        suffix = { Text(stringResource(R.string.gr)) }
                    )
                }
            }
            item {
                SegmentedButtonBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp),
                    data = listOf(Harm.Danger, Harm.Normal, Harm.Helthy, Harm.NotSpecified),
                    initial = Harm.NotSpecified,
                    rowCount = 4,
                    columnCount = 2,
                    borderWidth = 1.dp,
                    onSelect = { harm.value = it }
                ) { Text(it.harm) }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                        .fillMaxWidth()
                ) {
                    if (image.value.height == 1 && image.value.width == 1) {
                        Icon(
                            Icons.Outlined.Image,
                            null,
                            modifier = Modifier
                                .size(88.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    } else {
                        Image(
                            bitmap = image.value,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(88.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        )
                    }
                    OutlinedButton(onClick = {
                        picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }) {
                        Icon(Icons.Outlined.Save, null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(stringResource(R.string.choice), overflow = TextOverflow.Ellipsis, maxLines = 1)
                    }
                }
            }
        }
    }
}
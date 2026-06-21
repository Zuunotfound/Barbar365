package com.example.ui

import com.example.ui.theme.*
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconVisibility: ImageVector
    get() = ImageVector.Builder(
        name = "Visibility",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = androidx.compose.ui.graphics.SolidColor(androidx.compose.ui.graphics.Color.White)) {
            moveTo(12f, 4.5f)
            curveTo(7f, 4.5f, 2.73f, 7.61f, 1f, 12f)
            curveTo(2.73f, 16.39f, 7f, 19.5f, 12f, 19.5f)
            curveTo(17f, 19.5f, 21.27f, 16.39f, 23f, 12f)
            curveTo(21.27f, 7.61f, 17f, 4.5f, 12f, 4.5f)
            close()
            moveTo(12f, 17f)
            curveTo(9.24f, 17f, 7f, 14.76f, 7f, 12f)
            curveTo(7f, 9.24f, 9.24f, 7f, 12f, 7f)
            curveTo(14.76f, 7f, 17f, 9.24f, 17f, 12f)
            curveTo(17f, 14.76f, 14.76f, 17f, 12f, 17f)
            close()
            moveTo(12f, 9f)
            curveTo(10.34f, 9f, 9f, 10.34f, 9f, 12f)
            curveTo(9f, 13.66f, 10.34f, 15f, 12f, 15f)
            curveTo(13.66f, 15f, 15f, 13.66f, 15f, 12f)
            curveTo(15f, 10.34f, 13.66f, 9f, 12f, 9f)
            close()
        }
    }.build()

val IconVisibilityOff: ImageVector
    get() = ImageVector.Builder(
        name = "VisibilityOff",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = androidx.compose.ui.graphics.SolidColor(androidx.compose.ui.graphics.Color.White)) {
            moveTo(12f, 7f)
            curveTo(14.76f, 7f, 17f, 9.24f, 17f, 12f)
            curveTo(17f, 13.57f, 16.28f, 14.97f, 15.12f, 15.9f)
            lineTo(16.59f, 17.37f)
            curveTo(18.52f, 16.14f, 20.14f, 14.28f, 21.27f, 12f)
            curveTo(19.54f, 7.61f, 15.27f, 4.5f, 10.27f, 4.5f)
            curveTo(8.75f, 4.5f, 7.31f, 4.81f, 6f, 5.37f)
            lineTo(8.1f, 7.47f)
            curveTo(9.27f, 7.17f, 10.5f, 7f, 12f, 7f)
            close()
            moveTo(2f, 4.27f)
            lineTo(4.28f, 6.55f)
            curveTo(2.73f, 7.9f, 1.58f, 9.78f, 1f, 12f)
            curveTo(2.73f, 16.39f, 7f, 19.5f, 12f, 19.5f)
            curveTo(13.8f, 19.5f, 15.5f, 19.1f, 17f, 18.4f)
            lineTo(19.73f, 21.13f)
            lineTo(21f, 19.86f)
            lineTo(3.27f, 3f)
            lineTo(2f, 4.27f)
            close()
            moveTo(12f, 17f)
            curveTo(9.24f, 17f, 7f, 14.76f, 7f, 12f)
            curveTo(7f, 11.24f, 7.17f, 10.53f, 7.47f, 9.9f)
            lineTo(14.1f, 16.53f)
            curveTo(13.47f, 16.83f, 12.76f, 17f, 12f, 17f)
            close()
        }
    }.build()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: ZuuViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val context = LocalContext.current

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    Screen.Welcome -> WelcomeScreen(viewModel)
                    Screen.Login -> LoginScreen(viewModel)
                    Screen.Register -> RegisterScreen(viewModel)
                    Screen.PendingApproval -> PendingStatusScreen(viewModel)
                    Screen.Rejected -> RejectedStatusScreen(viewModel)
                    Screen.Suspended -> SuspendedStatusScreen(viewModel)
                    
                    // Admin Views
                    Screen.AdminDashboard -> AdminDashboardScreen(viewModel)
                    Screen.AdminEmployeesList -> AdminEmployeesScreen(viewModel)
                    Screen.AdminCreateTask -> AdminCreateTaskScreen(viewModel)
                    Screen.AdminReviewSubmissions -> AdminReviewSubmissionsScreen(viewModel)
                    Screen.AdminReviewBonusClaims -> AdminReviewClaimsScreen(viewModel)
                    Screen.AdminReviewPayments -> AdminReviewPaymentsScreen(viewModel)
                    Screen.AdminSupportTickets -> AdminSupportTicketsScreen(viewModel)
                    Screen.AdminNDPTracking -> AdminNDPTrackingScreen(viewModel)
                    Screen.AdminConfigSettings -> AdminConfigSettingsScreen(viewModel)
                    Screen.AdminContentCalendar -> AdminContentCalendarScreen(viewModel)
                    
                    // Owner Views
                    Screen.OwnerDashboard -> OwnerDashboardScreen(viewModel)
                    Screen.OwnerAdminManagement -> OwnerAdminManagementScreen(viewModel)
                    Screen.OwnerFinancialOverview -> OwnerFinancialOverviewScreen(viewModel)
                    
                    // Employee Views
                    Screen.EmployeeDashboard -> EmployeeDashboardScreen(viewModel)
                    Screen.Ms001Registration -> Ms001RegistrationScreen(viewModel)
                    Screen.EmployeeTasksList -> EmployeeTasksScreen(viewModel)
                    Screen.EmployeeSubmitProof -> EmployeeSubmitProofScreen(viewModel)
                    Screen.EmployeeClaimBonusFYP -> EmployeeClaimFypScreen(viewModel)
                    Screen.EmployeePaymentHistory -> EmployeePaymentScreen(viewModel)
                    Screen.EmployeeAnnouncements -> EmployeeAnnouncementsScreen(viewModel)
                    Screen.EmployeeSupportTickets -> EmployeeSupportTicketsScreen(viewModel)
                    Screen.EmployeeProfile -> EmployeeProfileScreen(viewModel)
                    
                    // Service Engineer Views
                    Screen.ServiceEngineerDashboard -> ServiceEngineerDashboardScreen(viewModel)
                }
            }
        }
    }
}

// --- CUSTOM ROYAL GEOMETRIC LOGO ---
@Composable
fun ZuuLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(90.dp)
            .background(
                Brush.radialGradient(
                    colors = listOf(ZuuAccentGold.copy(alpha = 0.25f), Color.Transparent),
                    radius = 160f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Draw elegant intersecting shapes
        Canvas(modifier = Modifier.size(72.dp)) {
            val width = size.width
            val height = size.height
            
            // Outer golden octagon/polygon path
            val path = Path().apply {
                val cx = width / 2
                val cy = height / 2
                val r = width * 0.45f
                for (i in 0 until 8) {
                    val angle = Math.toRadians((i * 45).toDouble())
                    val x = (cx + r * Math.cos(angle)).toFloat()
                    val y = (cy + r * Math.sin(angle)).toFloat()
                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                }
                close()
            }
            
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    colors = listOf(ZuuLightGold, ZuuAccentGold, Color(0xFFCA8A04))
                ),
                style = Stroke(width = 4.dp.toPx(), join = androidx.compose.ui.graphics.StrokeJoin.Round)
            )
            
            // Draw sleek inner intersecting lines for a brilliant "S" shape (SquadBarBar365)
            val linePath = Path().apply {
                moveTo(width * 0.65f, height * 0.35f)
                lineTo(width * 0.38f, height * 0.35f)
                lineTo(width * 0.38f, height * 0.5f)
                lineTo(width * 0.62f, height * 0.5f)
                lineTo(width * 0.62f, height * 0.65f)
                lineTo(width * 0.35f, height * 0.65f)
            }
            drawPath(
                path = linePath,
                brush = Brush.linearGradient(
                    colors = listOf(ZuuLightGold, ZuuAccentGold)
                ),
                style = Stroke(width = 6.dp.toPx(), cap = androidx.compose.ui.graphics.StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
            )
            
            // Tiny diamond lights
            drawCircle(
                color = ZuuLightGold,
                radius = 3.dp.toPx(),
                center = Offset(width / 2, height * 0.15f)
            )
            drawCircle(
                color = ZuuAccentGold,
                radius = 3.dp.toPx(),
                center = Offset(width / 2, height * 0.85f)
            )
        }
    }
}

// --- WELCOME SCREEN ---
@Composable
fun WelcomeScreen(viewModel: ZuuViewModel) {
    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(ZuuDeepPurpleBg, ZuuCardPurple))
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ZuuLogo()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "SQUADBARBAR365", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                color = Color.White, textAlign = TextAlign.Center
            )
            Text(
                "Private Employee Content Management Hub", fontSize = 14.sp,
                color = ZuuSoftLavender, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { viewModel.navigateTo(Screen.Login) },
                colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Masuk ke Akun", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { viewModel.navigateTo(Screen.Register) },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ZuuAccentGold),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(ZuuAccentGold, ZuuLightGold)))
            ) {
                Text("Daftar Karyawan Baru", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- LOGIN SCREEN ---
@Composable
fun LoginScreen(viewModel: ZuuViewModel) {
    var whatsapp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().background(ZuuDeepPurpleBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Masuk Akun", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Silakan gunakan nomor WhatsApp & sandi Anda.", color = ZuuSoftLavender, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(32.dp))

        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ZuuAccentGold,
            unfocusedBorderColor = ZuuBorderPurple,
            focusedLabelColor = ZuuAccentGold,
            unfocusedLabelColor = ZuuSoftLavender,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )

        OutlinedTextField(
            value = whatsapp, onValueChange = { whatsapp = it },
            label = { Text("Nomor WhatsApp") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        var isPasswordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) IconVisibility else IconVisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = ZuuSoftLavender
                    )
                }
            }
        )

        if (errorText != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(errorText!!, color = Color(0xFFEF4444), fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (whatsapp.isEmpty() || password.isEmpty()) {
                    errorText = "Semua bidang wajib diisi."
                } else {
                    viewModel.login(whatsapp, password) { err ->
                        if (err != null) {
                            errorText = err
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Masuk", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Kembali", color = ZuuAccentGold, modifier = Modifier.clickable {
                viewModel.navigateTo(Screen.Welcome)
            }, fontWeight = FontWeight.Bold
        )
    }
}

// --- REGISTER SCREEN ---
@Composable
fun RegisterScreen(viewModel: ZuuViewModel) {
    var fn by remember { mutableStateOf("") }
    var wa by remember { mutableStateOf("") }
    var psw by remember { mutableStateOf("") }
    var cpsw by remember { mutableStateOf("") }
    var otpEntered by remember { mutableStateOf("") }
    var generatedOtp by remember { mutableStateOf<String?>(null) }
    var isOtpSent by remember { mutableStateOf(false) }

    var isPswVisible by remember { mutableStateOf(false) }
    var isCpswVisible by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf<String?>(null) }
    val successMsg by viewModel.registrationSuccessMessage.collectAsState()
    val empIdReg by viewModel.registeredEmployeeId.collectAsState()
    val context = LocalContext.current

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ZuuAccentGold,
        unfocusedBorderColor = ZuuBorderPurple,
        focusedLabelColor = ZuuAccentGold,
        unfocusedLabelColor = ZuuSoftLavender,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedContainerColor = Color(0xFF15072B),
        unfocusedContainerColor = Color(0xFF15072B)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ZuuDeepPurpleBg)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (successMsg != null) {
            Icon(Icons.Default.CheckCircle, null, tint = ZuuAccentGold, modifier = Modifier.size(72.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text(successMsg ?: "Pendaftaran Berhasil", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text("ID Keanggotaan Anda:", color = ZuuSoftLavender, fontSize = 14.sp)
            Text(empIdReg ?: "", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = ZuuAccentGold)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Silakan kirim ID ini kepada pemilik atau admin untuk persetujuan keanggotaan.",
                color = ZuuSoftLavender, textAlign = TextAlign.Center, fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.registrationSuccessMessage.value = null
                    viewModel.navigateTo(Screen.Welcome)
                },
                colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Selesai & Ke Halaman Awal", fontWeight = FontWeight.Bold)
            }
        } else {
            Text("Pendaftaran Keanggotaan", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Lengkapi informasi berikut untuk mendaftar", color = ZuuSoftLavender, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = fn,
                onValueChange = { fn = it },
                label = { Text("Nama Lengkap") },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Person, null, tint = ZuuSoftLavender) }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = wa,
                onValueChange = { input ->
                    // Numeric only filter
                    if (input.all { it.isDigit() }) {
                        wa = input
                    }
                },
                label = { Text("Nomor WhatsApp") },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = { Icon(Icons.Default.Call, null, tint = ZuuSoftLavender) }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = psw,
                onValueChange = { psw = it },
                label = { Text("Password (Min 8 Karakter)") },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (isPswVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.Lock, null, tint = ZuuSoftLavender) },
                trailingIcon = {
                    IconButton(onClick = { isPswVisible = !isPswVisible }) {
                        Icon(
                            imageVector = if (isPswVisible) IconVisibility else IconVisibilityOff,
                            contentDescription = "Show/Hide Password",
                            tint = ZuuSoftLavender
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cpsw,
                onValueChange = { cpsw = it },
                label = { Text("Konfirmasi Password") },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (isCpswVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.Lock, null, tint = ZuuSoftLavender) },
                trailingIcon = {
                    IconButton(onClick = { isCpswVisible = !isCpswVisible }) {
                        Icon(
                            imageVector = if (isCpswVisible) IconVisibility else IconVisibilityOff,
                            contentDescription = "Show/Hide Confirm Password",
                            tint = ZuuSoftLavender
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // OTP Trigger Layout
            if (!isOtpSent) {
                Button(
                    onClick = {
                        if (fn.isBlank() || wa.isBlank() || psw.isBlank() || cpsw.isBlank()) {
                            errorText = "Lengkapi seluruh rincian terlebih dahulu."
                        } else if (psw.length < 8) {
                            errorText = "Password minimal terdiri dari 8 karakter."
                        } else if (psw != cpsw) {
                            errorText = "Konfirmasi password tidak cocok."
                        } else if (wa.length < 9) {
                            errorText = "Nomor WhatsApp tidak valid."
                        } else {
                            errorText = null
                            val code = (100000..999999).random().toString()
                            generatedOtp = code
                            isOtpSent = true
                            Toast.makeText(context, "Kode OTP berhasil dikirim ke nomor WhatsApp Anda!", Toast.LENGTH_LONG).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ZuuBorderPurple, contentColor = Color.White),
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Send, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Kirim Kode OTP via WA", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1145)),
                    border = BorderStroke(1.dp, ZuuBorderPurple)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "SIMULATOR OTP:",
                            fontWeight = FontWeight.Bold,
                            color = ZuuAccentGold,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "Kode verifikasi Anda yang dikirim via WhatsApp adalah: $generatedOtp",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = otpEntered,
                    onValueChange = { otpEntered = it },
                    label = { Text("Masukkan Kode OTP") },
                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = { Icon(Icons.Default.Check, null, tint = ZuuSoftLavender) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Kirim Ulang OTP",
                    color = ZuuAccentGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            val code = (100000..999999).random().toString()
                            generatedOtp = code
                            otpEntered = ""
                            Toast.makeText(context, "OTP dikirim ulang!", Toast.LENGTH_SHORT).show()
                        }
                        .padding(4.dp)
                )
            }

            if (errorText != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(errorText!!, color = Color(0xFFEF4444), fontSize = 13.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (fn.isEmpty() || wa.isEmpty() || psw.isEmpty() || cpsw.isEmpty()) {
                        errorText = "Wajib mengisi seluruh rincian."
                    } else if (psw != cpsw) {
                        errorText = "Konfirmasi password tidak cocok."
                    } else if (!isOtpSent) {
                        errorText = "Kirim dan verifikasi kode OTP WhatsApp Anda terlebih dahulu."
                    } else if (otpEntered != generatedOtp) {
                        errorText = "Kode OTP tidak valid atau tidak cocok."
                    } else {
                        errorText = null
                        viewModel.registerEmployee(fn, wa, psw) { ok ->
                            if (!ok) {
                                errorText = "Nomor WhatsApp sudah digunakan."
                            }
                        }
                    }
                },
                enabled = isOtpSent,
                colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Daftar Sekarang", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Kembali ke Halaman Utama",
                color = ZuuSoftLavender,
                modifier = Modifier.clickable { viewModel.navigateTo(Screen.Welcome) },
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
            )
        }
    }
}

// --- WAIT STATUS SCREENS ---
@Composable
fun PendingStatusScreen(viewModel: ZuuViewModel) {
    StatusFallback(
        title = "Menunggu Persetujuan",
        desc = "Keanggotaan Anda saat ini sedang dalam proses review oleh Admin BARBAR365. Silakan hubungi admin untuk aktivasi instan.",
        icon = Icons.Default.Info,
        color = Color(0xFFFBBF24),
        viewModel = viewModel
    )
}

@Composable
fun RejectedStatusScreen(viewModel: ZuuViewModel) {
    StatusFallback(
        title = "Pendaftaran Ditolak",
        desc = "Mohon maaf, pendaftaran akun Anda ditolak oleh Admin. Silakan periksa kelengkapan data diri dan kontak admin.",
        icon = Icons.Default.Close,
        color = Color(0xFFEF4444),
        viewModel = viewModel
    )
}

@Composable
fun SuspendedStatusScreen(viewModel: ZuuViewModel) {
    StatusFallback(
        title = "Akun Ditangguhkan",
        desc = "Akun Anda telah ditangguhkan karena pelanggaran aturan atau performa buruk beruntun (Warning SP-3). Hak gaji dinonaktifkan.",
        icon = Icons.Default.Warning,
        color = Color(0xFFEF4444),
        viewModel = viewModel
    )
}

@Composable
fun StatusFallback(title: String, desc: String, icon: ImageVector, color: Color, viewModel: ZuuViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().background(ZuuDeepPurpleBg).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, null, tint = color, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))
        Text(desc, color = ZuuSoftLavender, textAlign = TextAlign.Center, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { viewModel.logout() },
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text("Logout & Kembali ke Awal")
        }
    }
}

// --- ADMIN DASHBOARD ---
@Composable
fun AdminDashboardScreen(viewModel: ZuuViewModel) {
    val usersList by viewModel.users.collectAsState()
    val tasksList by viewModel.tasks.collectAsState()
    val submissionsList by viewModel.submissions.collectAsState()
    val claimsList by viewModel.bonusClaims.collectAsState()
    val paymentsList by viewModel.payments.collectAsState()
    val salariesList by viewModel.salaries.collectAsState()

    // Calculate Admin stats helper
    val totalEmp = usersList.count { it.role == "Employee" }
    val activeEmp = usersList.count { it.status == "Active" && it.role == "Employee" }
    val pendingEmp = usersList.count { it.status == "Pending" && it.role == "Employee" }
    val todayTasks = tasksList.size
    val pendingReview = submissionsList.count { it.status == "Pending Review" }
    val pendingSalary = salariesList.count { it.status == "Pending" }
    val pendingBonus = claimsList.count { it.status == "Pending Review" }

    AdminDrawerScaffold(viewModel = viewModel, title = "Admin Dashboard") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header Greeting Banner
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1D0B33)),
                    border = BorderStroke(1.dp, ZuuBorderPurple),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Halo, Admin!",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZuuAccentGold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Kelola tim harian, review bukti, dan pantau target NDP dengan mudah hari ini.",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.8f),
                                lineHeight = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(ZuuAccentGold.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = ZuuAccentGold,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }

            // Real-time stats header
            item {
                Column {
                    Text(
                        text = "Ringkasan Statistik Real-time",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AdminCardStat("Total Karyawan", "$totalEmp Orang", Modifier.weight(1f), Color(0xFF3B82F6))
                        AdminCardStat("Menunggu Approval", "$pendingEmp Menunggu", Modifier.weight(1f), Color(0xFFFBBF24))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AdminCardStat("Daftar Tugas Aktif", "$todayTasks Tugas", Modifier.weight(1f), Color(0xFF10B981))
                        AdminCardStat("Review Bukti Pending", "$pendingReview Post", Modifier.weight(1f), Color(0xFFF59E0B))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AdminCardStat("Pengajuan Gaji", "$pendingSalary Antrian", Modifier.weight(1f), Color(0xFFEC4899))
                        AdminCardStat("Klaim Bonus FYP", "$pendingBonus Berkas", Modifier.weight(1f), Color(0xFF8B5CF6))
                    }
                }
            }

            // Beautiful Custom Chart
            item {
                Text(
                    text = "Grafik Produktivitas Mingguan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                    border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.8f)),
                    modifier = Modifier.fillMaxWidth().height(220.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Distribusi Target Nilai NDP Rata-Rata",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZuuSoftLavender
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF10B981).copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Tren Naik",
                                    color = Color(0xFF10B981),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        Canvas(modifier = Modifier.fillMaxSize().weight(1f)) {
                            val points = listOf(14f, 19f, 22f, 17f, 26f, 31f, 36f)
                            val labels = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
                            val maxVal = 40f
                            val width = size.width
                            val height = size.height
                            val spacing = width / 6
                            
                            // Background grid lines
                            for (i in 1..4) {
                                val yItem = height * i / 5
                                drawLine(
                                    color = Color.White.copy(alpha = 0.05f),
                                    start = Offset(0f, yItem),
                                    end = Offset(width, yItem),
                                    strokeWidth = 1.dp.toPx()
                                )
                            }

                            // Dynamic smooth curves connection
                            for (i in 0 until points.size - 1) {
                                val x1 = i * spacing
                                val y1 = height - (points[i] / maxVal) * height
                                val x2 = (i + 1) * spacing
                                val y2 = height - (points[i + 1] / maxVal) * height
                                
                                drawLine(
                                    color = ZuuAccentGold,
                                    start = Offset(x1, y1),
                                    end = Offset(x2, y2),
                                    strokeWidth = 3.dp.toPx()
                                )
                                drawCircle(
                                    color = ZuuLightGold,
                                    radius = 6.dp.toPx(),
                                    center = Offset(x1, y1)
                                )
                            }
                            
                            // Highlight final point
                            val lastIdx = points.size - 1
                            drawCircle(
                                color = Color.White,
                                radius = 8.dp.toPx(),
                                center = Offset(lastIdx * spacing, height - (points.last() / maxVal) * height)
                            )
                            drawCircle(
                                color = Color(0xFF10B981),
                                radius = 5.dp.toPx(),
                                center = Offset(lastIdx * spacing, height - (points.last() / maxVal) * height)
                            )
                        }
                    }
                }
            }

            // Quick Execution Action Hub
            item {
                Text(
                    text = "Akses Operasional Cepat",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AdminCommandButton(
                            title = "Kelola Karyawan",
                            icon = Icons.Default.Person,
                            color = Color(0xFF3B82F6),
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.navigateTo(Screen.AdminEmployeesList) }
                        )
                        AdminCommandButton(
                            title = "Buat Tugas",
                            icon = Icons.Default.Add,
                            color = Color(0xFF10B981),
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.navigateTo(Screen.AdminCreateTask) }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AdminCommandButton(
                            title = "Review Bukti",
                            icon = Icons.Default.Edit,
                            color = Color(0xFFEC4899),
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.navigateTo(Screen.AdminReviewSubmissions) }
                        )
                        AdminCommandButton(
                            title = "Setelan Hub",
                            icon = Icons.Default.Settings,
                            color = Color(0xFF8B5CF6),
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.navigateTo(Screen.AdminConfigSettings) }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AdminCommandButton(
                            title = "Kalender Konten",
                            icon = Icons.Default.DateRange,
                            color = Color(0xFFFBBF24),
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.navigateTo(Screen.AdminContentCalendar) }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun AdminCardStat(title: String, score: String, modifier: Modifier = Modifier, color: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1D0B33)),
        border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.5f)),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ZuuSoftLavender
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = score,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}

@Composable
fun AdminCommandButton(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1D0B33)),
        border = BorderStroke(1.dp, ZuuBorderPurple),
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

// --- ADMIN SIDE NAVIGATION STRUCT (SCAFFOLD WRAPPER) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDrawerScaffold(viewModel: ZuuViewModel, title: String, content: @Composable () -> Unit) {
    val navItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = ZuuAccentGold,
        selectedTextColor = ZuuAccentGold,
        unselectedIconColor = ZuuSoftLavender,
        unselectedTextColor = ZuuSoftLavender,
        indicatorColor = ZuuBorderPurple
    )

    Box(modifier = Modifier.fillMaxSize().background(ZuuDeepPurpleBg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ZuuDarkAppBar),
                actions = {
                    val user by viewModel.currentUser.collectAsState()
                    if (user?.role == "Owner") {
                        IconButton(onClick = { viewModel.navigateTo(Screen.OwnerDashboard) }) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Panel Owner",
                                tint = ZuuAccentGold
                            )
                        }
                    }
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(Icons.Default.ExitToApp, "Logout", tint = Color.White)
                    }
                }
            )
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
            // Admin bottom short bar helper
            NavigationBar(containerColor = ZuuDarkAppBar) {
                NavigationBarItem(
                    selected = title == "Admin Dashboard",
                    onClick = { viewModel.navigateTo(Screen.AdminDashboard) },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Dashboard", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Kelola Karyawan",
                    onClick = { viewModel.navigateTo(Screen.AdminEmployeesList) },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Karyawan", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Review Bukti",
                    onClick = { viewModel.navigateTo(Screen.AdminReviewSubmissions) },
                    icon = { Icon(Icons.Default.Edit, null) },
                    label = { Text("Review", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Konfigurasi Hub",
                    onClick = { viewModel.navigateTo(Screen.AdminConfigSettings) },
                    icon = { Icon(Icons.Default.Settings, null) },
                    label = { Text("Setelan", fontSize = 10.sp) },
                    colors = navItemColors
                )
            }
        }
    }
}

// --- ADMIN EMPLOYEES CONTROL SCREEN ---
@Composable
fun AdminEmployeesScreen(viewModel: ZuuViewModel) {
    val usersList by viewModel.users.collectAsState()
    val submissionsList by viewModel.submissions.collectAsState()
    val checkinsList by viewModel.checkins.collectAsState()
    val warningsList by viewModel.warnings.collectAsState()
    val tasksList by viewModel.tasks.collectAsState()

    var selectedEmpForWarning by remember { mutableStateOf<User?>(null) }
    var warningTextForm by remember { mutableStateOf("") }
    var warningLevelForm by remember { mutableStateOf(1) }

    AdminDrawerScaffold(viewModel = viewModel, title = "Kelola Karyawan") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Monitoring Status & SP Karyawan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            items(usersList.filter { it.role == "Employee" }) { emp ->
                var showStatusOptions by remember { mutableStateOf(false) }

                // Calculate calculations live
                val empTasks = submissionsList.filter { it.employeeId == emp.employeeId }
                val empCheckins = checkinsList.filter { it.employeeId == emp.employeeId }
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(emp.fullName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(emp.employeeId, color = Color.LightGray, fontSize = 12.sp)
                                Text("WhatsApp: ${emp.whatsappNumber}", color = Color.LightGray, fontSize = 12.sp)
                                if (emp.telegramUsername.isNotEmpty()) {
                                    Text("Telegram: ${emp.telegramUsername}", color = Color.LightGray, fontSize = 12.sp)
                                }
                                if (emp.bankName.isNotEmpty()) {
                                    Text("Withdraw: ${emp.bankName} - ${emp.bankAccountNumber} (a.n. ${emp.bankAccountName})", color = Color(0xFFFBBF24), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                } else {
                                    Text("Withdraw: Belum diatur", color = Color.Gray, fontSize = 12.sp)
                                }
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Box(
                                    modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                        .background(
                                            when (emp.status) {
                                                "Active" -> Color(0xFF10B981)
                                                "Pending" -> Color(0xFFFBBF24)
                                                "Suspended" -> Color(0xFFEF4444)
                                                else -> Color.Gray
                                            }
                                        ).padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(emp.status, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                                if (emp.warningLevel > 0) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("SP-${emp.warningLevel}", color = Color.Red, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Performance Rates
                        val approvedSub = empTasks.count { it.status == "Approved" }
                        val rejectedSub = empTasks.count { it.status == "Rejected" }
                        val rate = if (tasksList.isNotEmpty()) (approvedSub * 100) / tasksList.size else 100
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Penyelesaian: $rate%", color = Color.White, fontSize = 12.sp)
                            Text("| Check-In: ${empCheckins.size}", color = Color.White, fontSize = 12.sp)
                            val isEligible = viewModel.calculateSalaryEligibility(
                                emp.employeeId, emp.fullName, tasksList, empTasks, empCheckins,
                                warningsList.filter { it.employeeId == emp.employeeId }, emp.status == "Suspended"
                            )
                            val eligibleColor = if (isEligible == "Eligible") Color(0xFF10B981) else Color(0xFFEF4444)
                            Text("| Kelayakan Gaji: $isEligible", color = eligibleColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { showStatusOptions = !showStatusOptions },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF475569)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Aksi Status", fontSize = 12.sp)
                            }
                            Button(
                                onClick = { selectedEmpForWarning = emp },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Terbitkan SP", fontSize = 12.sp)
                            }
                        }

                        if (showStatusOptions) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(onClick = { viewModel.updateEmployeeStatus(emp, "Active"); showStatusOptions = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))) {
                                    Text("Aktif", fontSize = 10.sp)
                                }
                                Button(onClick = { viewModel.updateEmployeeStatus(emp, "Suspended"); showStatusOptions = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24))) {
                                    Text("Suspend", fontSize = 10.sp)
                                }
                                Button(onClick = { viewModel.updateEmployeeStatus(emp, "Rejected"); showStatusOptions = false }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))) {
                                    Text("Tolak", fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // SP Dialog Warning Form
    if (selectedEmpForWarning != null) {
        AlertDialog(
            onDismissRequest = { selectedEmpForWarning = null },
            title = { Text("Terbitkan Surat Peringatan (SP)", color = Color.White) },
            text = {
                Column {
                    Text("Karyawan: ${selectedEmpForWarning!!.fullName}", color = Color.LightGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Pilih Tingkat SP:", color = Color.White)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        (1..3).forEach { lvl ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = warningLevelForm == lvl, onClick = { warningLevelForm = lvl })
                                Text("SP-$lvl", color = Color.White)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = warningTextForm, onValueChange = { warningTextForm = it },
                        label = { Text("Alasan Penerbitan SP") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.issueWarning(selectedEmpForWarning!!, warningLevelForm, warningTextForm)
                        selectedEmpForWarning = null
                        warningTextForm = ""
                    }
                ) {
                    Text("Terbitkan")
                }
            },
            dismissButton = {
                Button(onClick = { selectedEmpForWarning = null }) { Text("Batal") }
            },
            containerColor = Color(0xFF1E293B)
        )
    }
}

// --- ADMIN CREATE TASK SCREEN ---
@Composable
fun AdminCreateTaskScreen(viewModel: ZuuViewModel) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var cat by remember { mutableStateOf("Instagram") }
    var caption by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    val context = LocalContext.current

    AdminDrawerScaffold(viewModel = viewModel, title = "Buat Tugas Baru") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Formulir Rilis Tugas Harian", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            item {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul Tugas") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Deskripsi Singkat") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())
            }
            item {
                Text("Kategori Media Sosial:", color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    listOf("Instagram", "TikTok", "Facebook", "YouTube Shorts").forEach { targetCat ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = cat == targetCat, onClick = { cat = targetCat })
                            Text(targetCat, color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
            item {
                OutlinedTextField(value = caption, onValueChange = { caption = it }, label = { Text("Template Caption") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(value = tags, onValueChange = { tags = it }, label = { Text("Template Hashtags") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())
            }
            item {
                OutlinedTextField(value = instructions, onValueChange = { instructions = it }, label = { Text("Instruksi Langkah Kerja") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())
            }

            item {
                Button(
                    onClick = {
                        if (title.isEmpty() || desc.isEmpty() || instructions.isEmpty()) {
                            Toast.makeText(context, "Mohon lengkapi data wajib.", Toast.LENGTH_SHORT).show()
                        } else {
                            val newTask = Task(
                                title = title,
                                description = desc,
                                contentCategory = cat,
                                caption = caption,
                                hashtags = tags,
                                instructions = instructions,
                                deadline = System.currentTimeMillis() + (3600 * 24 * 1000 * 2) // 2 days
                            )
                            viewModel.createTask(newTask)
                            Toast.makeText(context, "Tugas berhasil diterbitkan!", Toast.LENGTH_SHORT).show()
                            viewModel.navigateTo(Screen.AdminDashboard)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Tautkan & Posting Tugas", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// --- ADMIN REVIEW TASK SUBMISSIONS ---
@Composable
fun AdminReviewSubmissionsScreen(viewModel: ZuuViewModel) {
    val submissionsList by viewModel.submissions.collectAsState()
    var selectedForReview by remember { mutableStateOf<TaskSubmission?>(null) }
    var rejectionReasonText by remember { mutableStateOf("") }

    AdminDrawerScaffold(viewModel = viewModel, title = "Review Bukti") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Penerimaan Bukti Posting Karyawan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            val pendingList = submissionsList.filter { it.status == "Pending Review" }
            if (pendingList.isEmpty()) {
                item {
                    Text("Belum ada ajuan bukti baru hari ini.", color = Color.LightGray, modifier = Modifier.padding(16.dp))
                }
            } else {
                items(pendingList) { sub ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(sub.taskTitle, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Dari: ${sub.employeeName} (${sub.employeeId})", color = Color.LightGray, fontSize = 14.sp)
                            Text("Platform: ${sub.platform}", color = Color.LightGray, fontSize = 14.sp)
                            Text("Tautan: ${sub.postLink}", color = Color(0xFF10B981), fontSize = 14.sp, modifier = Modifier.clickable {  })
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            val hasRealScreenshot = sub.screenshotPath.isNotEmpty() && sub.screenshotPath != "Proof" && sub.screenshotPath != "mock_screenshot_path"
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(ZuuCardPurple)
                                    .border(1.dp, ZuuBorderPurple, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (hasRealScreenshot) {
                                    AsyncImage(
                                        model = sub.screenshotPath,
                                        contentDescription = "Bukti Screenshot Karyawan",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                    )
                                } else {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
                                        Icon(Icons.Default.CheckCircle, null, tint = ZuuAccentGold, modifier = Modifier.size(24.dp))
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text("Bukti Screenshot Terlampir", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("Penyimpanan lokal sistem sukses diuji", color = ZuuSoftLavender, fontSize = 11.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { viewModel.reviewSubmission(sub, true, "") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Setujui", fontSize = 12.sp)
                                }
                                Button(
                                    onClick = { selectedForReview = sub },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Tolak", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (selectedForReview != null) {
        AlertDialog(
            onDismissRequest = { selectedForReview = null },
            title = { Text("Tolak Bukti Posting", color = Color.White) },
            text = {
                Column {
                    Text("Alasan Penolakan wajib diisi untuk mendidik Karyawan:", color = Color.LightGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = rejectionReasonText, onValueChange = { rejectionReasonText = it },
                        label = { Text("Alasan") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (rejectionReasonText.isNotEmpty()) {
                            viewModel.reviewSubmission(selectedForReview!!, false, rejectionReasonText)
                            selectedForReview = null
                            rejectionReasonText = ""
                        }
                    }
                ) {
                    Text("Kirim Penolakan")
                }
            },
            dismissButton = {
                Button(onClick = { selectedForReview = null }) { Text("Batal") }
            },
            containerColor = Color(0xFF1E293B)
        )
    }
}

// --- ADMIN REVIEW CLAIMS BONUS SCREEN ---
@Composable
fun AdminReviewClaimsScreen(viewModel: ZuuViewModel) {
    val claimsList by viewModel.bonusClaims.collectAsState()

    AdminDrawerScaffold(viewModel = viewModel, title = "Review Klaim FYP") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Permintaan Klaim Bonus Insentif FYP", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            val pending = claimsList.filter { it.status == "Pending Review" }
            if (pending.isEmpty()) {
                item {
                    Text("Tidak ada klaim bonus baru saat ini.", color = Color.LightGray, modifier = Modifier.padding(16.dp))
                }
            } else {
                items(pending) { claim ->
                    var rAmount by remember { mutableStateOf(claim.recommendedReward.toString()) }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Nama: ${claim.employeeName} (${claim.employeeId})", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Platform Link: ${claim.platformLink}", color = Color.LightGray, fontSize = 12.sp)
                            Text("Tayangan Insights: ${claim.viewCount} views", color = Color.White, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Rekomendasi Reward: Rp ${claim.recommendedReward}", color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
                            
                            val hasRealScreenshot = claim.screenshotInsights.isNotEmpty() && claim.screenshotInsights != "mock_insights"
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(ZuuCardPurple)
                                    .border(1.dp, ZuuBorderPurple, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (hasRealScreenshot) {
                                    AsyncImage(
                                        model = claim.screenshotInsights,
                                        contentDescription = "Bukti Screenshot Insights",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                    )
                                } else {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
                                        Icon(Icons.Default.CheckCircle, null, tint = ZuuAccentGold, modifier = Modifier.size(24.dp))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("Bukti Insights Tersimpan", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Text("Validasi penyimpanan internal otomatis", color = ZuuSoftLavender, fontSize = 10.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = rAmount, onValueChange = { rAmount = it },
                                label = { Text("Koreksi Jumlah Validasi (Rp)") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { 
                                        viewModel.reviewBonusClaim(claim, true, rAmount.toDoubleOrNull() ?: claim.recommendedReward, "") 
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Setujui Pembayaran")
                                }
                                Button(
                                    onClick = { viewModel.reviewBonusClaim(claim, false, 0.0, "Klaim views tidak valid") },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Tolak", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- ADMIN REVIEW PAYMENTS SCREEN ---
@Composable
fun AdminReviewPaymentsScreen(viewModel: ZuuViewModel) {
    val paymentsList by viewModel.payments.collectAsState()
    val usersList by viewModel.users.collectAsState()
    val withdrawalRequests by viewModel.withdrawalRequests.collectAsState()
    val context = LocalContext.current
    
    var selectedTab by remember { mutableStateOf(0) }

    AdminDrawerScaffold(viewModel = viewModel, title = "Keuangan & Gaji") {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Manajemen Keuangan & Siklus Gaji",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Navigation Row (Segmented Control Tabs)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF15072B), RoundedCornerShape(10.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf("Transfer Log", "Gaji Rutin 🗓️", "Tarikan WD").forEachIndexed { index, label ->
                    Button(
                        onClick = { selectedTab = index },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTab == index) ZuuAccentGold else Color.Transparent,
                            contentColor = if (selectedTab == index) ZuuDeepPurpleBg else Color.White
                        ),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> {
                    // Log Pembayaran / Transfer Log
                    if (paymentsList.isEmpty()) {
                        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text("Belum ada arsip pembayaran.", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(paymentsList) { pay ->
                                val emp = usersList.find { it.employeeId == pay.employeeId }
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(pay.employeeName, color = Color.White, fontWeight = FontWeight.Bold)
                                            Text(pay.paymentType + " • " + pay.dateString, color = Color.LightGray, fontSize = 11.sp)
                                            Text("Rp " + String.format("%,.0f", pay.amount), color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
                                            emp?.let {
                                                if (it.bankName.isNotEmpty()) {
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                    Text("Rek: ${it.bankName} - ${it.bankAccountNumber}", color = Color(0xFFFBBF24), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                                    Text("a/n: ${it.bankAccountName}", color = Color.LightGray, fontSize = 11.sp)
                                                }
                                            }
                                        }
                                        Button(
                                            onClick = { 
                                                viewModel.updatePayment(pay.copy(status = "Paid"))
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = if (pay.status == "Paid") Color.DarkGray else Color(0xFF10B981)),
                                            enabled = pay.status != "Paid",
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(if (pay.status == "Paid") "Lunas" else "Bayar", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Gaji Rutin (Berdasarkan Hari Masuk Karyawan)
                    val employees = usersList.filter { it.role == "Employee" && it.status == "Active" }
                    val todayInEnglish = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date())
                    val todayIndo = when (todayInEnglish) {
                        "Monday" -> "Senin"
                        "Tuesday" -> "Selasa"
                        "Wednesday" -> "Rabu"
                        "Thursday" -> "Kamis"
                        "Friday" -> "Jumat"
                        "Saturday" -> "Sabtu"
                        "Sunday" -> "Minggu"
                        else -> todayInEnglish
                    }

                    Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                            border = BorderStroke(1.dp, ZuuBorderPurple),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                        ) {
                            Text(
                                text = "📅 Hari Ini: $todayIndo. Karyawan yang terdaftar di hari $todayIndo akan ditandai dengan badge khusus untuk gajian rutin mingguan.",
                                color = ZuuSoftLavender,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(12.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        if (employees.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Tidak ada karyawan aktif untuk digaji.", color = Color.Gray)
                            }
                        } else {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                items(employees) { emp ->
                                    val joinDay = viewModel.getJoinDayOfWeek(emp.registeredAt)
                                    val isPaydayToday = joinDay.equals(todayIndo, ignoreCase = true)
                                    
                                    var customPayStr by remember { mutableStateOf("150000") }

                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                        border = if (isPaydayToday) BorderStroke(1.5.dp, ZuuAccentGold) else null,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(14.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column {
                                                    Text(emp.fullName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                    Text("ID: ${emp.employeeId} • Hari Masuk: $joinDay", color = Color.LightGray, fontSize = 11.sp)
                                                    Text("Saldo Dompet: Rp " + String.format("%,.0f", emp.walletBalance), color = ZuuAccentGold, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                                }
                                                if (isPaydayToday) {
                                                    Box(
                                                        modifier = Modifier
                                                            .background(ZuuAccentGold, RoundedCornerShape(6.dp))
                                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                                    ) {
                                                        Text("GAJIAN HARI INI 💥", color = ZuuDeepPurpleBg, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(12.dp))
                                            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
                                            Spacer(modifier = Modifier.height(10.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                OutlinedTextField(
                                                    value = customPayStr,
                                                    onValueChange = { customPayStr = it },
                                                    label = { Text("Jumlah Gaji", fontSize = 10.sp, color = Color.Gray) },
                                                    textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
                                                    modifier = Modifier.weight(1.3f).height(52.dp),
                                                    colors = OutlinedTextFieldDefaults.colors(
                                                        focusedBorderColor = ZuuAccentGold,
                                                        unfocusedBorderColor = ZuuBorderPurple
                                                    ),
                                                    shape = RoundedCornerShape(8.dp)
                                                )

                                                Button(
                                                    onClick = {
                                                        val amount = customPayStr.toDoubleOrNull()
                                                        if (amount == null || amount <= 0) {
                                                            Toast.makeText(context, "Jumlah gaji tidak valid!", Toast.LENGTH_SHORT).show()
                                                        } else {
                                                            viewModel.paySalaryToEmployee(
                                                                employeeId = emp.employeeId,
                                                                amount = amount,
                                                                reason = "Gaji Mingguan ($joinDay)",
                                                                onSuccess = {
                                                                    (context as? android.app.Activity)?.runOnUiThread {
                                                                        Toast.makeText(context, "Berhasil mengirim gaji Rp " + String.format("%,.0f", amount) + " ke dompet ${emp.fullName}!", Toast.LENGTH_LONG).show()
                                                                    }
                                                                }
                                                            )
                                                        }
                                                    },
                                                    colors = ButtonDefaults.buttonColors(containerColor = if (isPaydayToday) ZuuAccentGold else ZuuBorderPurple),
                                                    modifier = Modifier.weight(1f).height(42.dp),
                                                    shape = RoundedCornerShape(8.dp),
                                                    contentPadding = PaddingValues(0.dp)
                                                ) {
                                                    Text("Bayar Gaji", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isPaydayToday) ZuuDeepPurpleBg else Color.White)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                2 -> {
                    // Daftar Penarikan Dana (Withdraw / WD) Karyawan
                    if (withdrawalRequests.isEmpty()) {
                        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text("Tidak ada antrian penarikan dana (WD).", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(withdrawalRequests) { req ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                    border = if (req.status == "Pending") BorderStroke(1.5.dp, ZuuAccentGold) else null,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(req.employeeName, color = Color.White, fontWeight = FontWeight.Bold)
                                                Text("ID Karyawan: ${req.employeeId}", color = Color.LightGray, fontSize = 11.sp)
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        when (req.status) {
                                                            "Pending" -> Color(0xFFFBBF24).copy(alpha = 0.2f)
                                                            "Approved" -> @Suppress("DEPRECATION") Color(0xFF10B981).copy(alpha = 0.2f)
                                                            else -> Color(0xFFEF4444).copy(alpha = 0.2f)
                                                        },
                                                        RoundedCornerShape(6.dp)
                                                    )
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text(
                                                    text = req.status,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = when (req.status) {
                                                        "Pending" -> Color(0xFFFBBF24)
                                                        "Approved" -> Color(0xFF10B981)
                                                        else -> Color(0xFFEF4444)
                                                    }
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(10.dp))
                                        HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
                                        Spacer(modifier = Modifier.height(10.dp))

                                        Text(
                                            text = "Metode: ${req.bankName}\nNo. Rekening: ${req.bankAccountNumber}\na/n: ${req.bankAccountName}",
                                            color = ZuuSoftLavender,
                                            fontSize = 11.sp,
                                            lineHeight = 16.sp
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "Jumlah Penarikan: Rp " + String.format("%,.0f", req.amount),
                                            color = Color(0xFF10B981),
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 15.sp
                                        )

                                        if (req.status == "Pending") {
                                            Spacer(modifier = Modifier.height(12.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Button(
                                                    onClick = { viewModel.rejectWithdrawal(req.id) },
                                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                                    modifier = Modifier.weight(1f),
                                                    shape = RoundedCornerShape(8.dp)
                                                ) {
                                                    Text("Tolak & Refund", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                                }

                                                Button(
                                                    onClick = { viewModel.approveWithdrawal(req.id) },
                                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                                    modifier = Modifier.weight(1f),
                                                    shape = RoundedCornerShape(8.dp)
                                                ) {
                                                    Text("Setujui / Transfer", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- ADMIN DELEGATE HUB CONFIG SETTINGS ---
@Composable
fun AdminConfigSettingsScreen(viewModel: ZuuViewModel) {
    val minCRate by viewModel.minCompletionRate.collectAsState()
    val maxRej by viewModel.maxRejectionCount.collectAsState()

    var tempRate by remember { mutableStateOf(minCRate.toString()) }
    var tempRej by remember { mutableStateOf(maxRej.toString()) }
    
    val context = LocalContext.current

    AdminDrawerScaffold(viewModel = viewModel, title = "Konfigurasi Hub") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(androidx.compose.foundation.rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Setelan Engine SquadBarBar365", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Atur toleransi parameter sistem kelayakan gaji mingguan karyawan harian.", color = Color.LightGray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = tempRate, onValueChange = { tempRate = it },
                label = { Text("Minimal Persentase Penyelesaian Tugas (%)", color = Color.LightGray) },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tempRej, onValueChange = { tempRej = it },
                label = { Text("Maksimal Penolakan Tugas Diijinkan Saja", color = Color.LightGray) },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val r = tempRate.toIntOrNull()
                    val rj = tempRej.toIntOrNull()
                    if (r != null && rj != null) {
                        viewModel.saveConfigSettings(r, rj)
                        Toast.makeText(context, "Sistem berhasil dirumuskan ulang!", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Simpan Toleransi Sistem", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.purgeAndResetDatabase {
                        Toast.makeText(context, "Database dibersihkan 100%! Silakan buat akun Admin baru di menu pendaftaran.", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("HAPUS & RESET DATABASE LOKAL", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

// --- EMPLOYEE MAIN DASHBOARD SCREEN ---
@Composable
fun EmployeeDashboardScreen(viewModel: ZuuViewModel) {
    val user by viewModel.currentUser.collectAsState()
    val tasksList by viewModel.tasks.collectAsState()
    val checkinsList by viewModel.checkins.collectAsState()
    val submissionsList by viewModel.submissions.collectAsState()
    val warningList by viewModel.warnings.collectAsState()
    val paymentList by viewModel.payments.collectAsState()
    val announcementList by viewModel.announcements.collectAsState()

    if (user == null) return

    val mySubmissions = submissionsList.filter { it.employeeId == user!!.employeeId }
    val myCheckins = checkinsList.filter { it.employeeId == user!!.employeeId }
    val empWarningList = warningList.filter { it.employeeId == user!!.employeeId }

    // Estimate Earnings
    val myPayments = paymentList.filter { it.employeeId == user!!.employeeId }
    val estEarnings = myPayments.sumOf { it.amount }

    EmployeeScaffold(viewModel = viewModel, title = "SquadBarBar365") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(ZuuBorderPurple, ZuuAccentGold.copy(alpha = 0.3f))))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("Selamat Datang Kembali,", color = ZuuSoftLavender, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(user!!.fullName, color = ZuuAccentGold, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("ID Karyawan: ${user!!.employeeId} | ${user!!.whatsappNumber}", color = ZuuSoftLavender, fontSize = 12.sp)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Check if already checked in today
                        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        val checkedInToday = myCheckins.any { it.dateString == todayStr }
                        
                        val calendar = java.util.Calendar.getInstance()
                        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
                        val isPastNoon = hour >= 12
                        
                        if (!checkedInToday) {
                            if (isPastNoon) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF7F1D1D)),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF87171)),
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.Warning, null, tint = Color.White, modifier = Modifier.size(24.dp))
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text("Absensi Hari Ini Ditutup", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("Batas waktu absen maksimal pkl 12:00 siang. Hubungi Admin jika terlambat.", color = Color(0xFFFECACA), fontSize = 12.sp, textAlign = TextAlign.Center)
                                    }
                                }
                            } else {
                                Button(
                                    onClick = { viewModel.checkIn(user!!.employeeId) },
                                    colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(Icons.Default.PlayArrow, null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Saya Siap Bekerja Hari Ini (Absen)", fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(ZuuBorderPurple.copy(alpha = 0.5f))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, null, tint = ZuuAccentGold)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Anda Siap Bekerja & Absen Hari Ini", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            // Quick Stats Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("Estimasi Gaji", fontSize = 11.sp, color = ZuuSoftLavender)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Rp ${estEarnings.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = ZuuAccentGold)
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("Checkins Pekan Ini", fontSize = 11.sp, color = ZuuSoftLavender)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${myCheckins.size} Absen", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            // Announcement Widget
            item {
                val pinned = announcementList.firstOrNull { it.isPinned }
                if (pinned != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = ZuuBorderPurple.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(ZuuBorderPurple, ZuuAccentGold.copy(alpha = 0.2f))))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Notifications, null, tint = ZuuAccentGold, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("PENGUMUMAN PINNED", color = ZuuAccentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(pinned.title, color = Color.White, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(pinned.content, color = ZuuSoftLavender, fontSize = 12.sp)
                        }
                    }
                }
            }

            // Action Quick Buttons Matrix Menu
            item {
                Text("Menu Pintasan", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.navigateTo(Screen.EmployeeTasksList) },
                        colors = ButtonDefaults.buttonColors(containerColor = ZuuBorderPurple, contentColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Tugas", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { viewModel.navigateTo(Screen.EmployeeClaimBonusFYP) },
                        colors = ButtonDefaults.buttonColors(containerColor = ZuuBorderPurple, contentColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("FYP Bonus", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { viewModel.navigateTo(Screen.EmployeePaymentHistory) },
                        colors = ButtonDefaults.buttonColors(containerColor = ZuuBorderPurple, contentColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Keuangan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// --- EMPLOYEE MAIN SCAFFOLD ROUTE ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScaffold(viewModel: ZuuViewModel, title: String, content: @Composable () -> Unit) {
    val navItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = ZuuAccentGold,
        selectedTextColor = ZuuAccentGold,
        unselectedIconColor = ZuuSoftLavender,
        unselectedTextColor = ZuuSoftLavender,
        indicatorColor = ZuuBorderPurple
    )

    Box(modifier = Modifier.fillMaxSize().background(ZuuDeepPurpleBg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ZuuDarkAppBar),
                actions = {
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(Icons.Default.ExitToApp, "Logout", tint = Color.White)
                    }
                }
            )
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
            // Bottom navigation menu
            NavigationBar(containerColor = ZuuDarkAppBar) {
                NavigationBarItem(
                    selected = title == "SquadBarBar365",
                    onClick = { viewModel.navigateTo(Screen.EmployeeDashboard) },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Hub", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Daftar Tugas",
                    onClick = { viewModel.navigateTo(Screen.EmployeeTasksList) },
                    icon = { Icon(Icons.Default.CheckCircle, null) },
                    label = { Text("Tugas", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Klaim Bonus FYP",
                    onClick = { viewModel.navigateTo(Screen.EmployeeClaimBonusFYP) },
                    icon = { Icon(Icons.Default.Star, null) },
                    label = { Text("Claims", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Dukungan Help",
                    onClick = { viewModel.navigateTo(Screen.EmployeeSupportTickets) },
                    icon = { Icon(Icons.Default.Call, null) },
                    label = { Text("Support", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Profil Saya",
                    onClick = { viewModel.navigateTo(Screen.EmployeeProfile) },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile", fontSize = 10.sp) },
                    colors = navItemColors
                )
            }
        }
    }
}

// --- EMPLOYEE TASKS LIST SCREEN ---
@Composable
fun EmployeeTasksScreen(viewModel: ZuuViewModel) {
    val tasksList by viewModel.tasks.collectAsState()
    val submissionsList by viewModel.submissions.collectAsState()
    val user by viewModel.currentUser.collectAsState()
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    EmployeeScaffold(viewModel = viewModel, title = "Daftar Tugas") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Tugas Harian Anda", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Selesaikan tugas posting harian untuk menjaga bonus & gaji.", fontSize = 12.sp, color = Color.LightGray)
            }

            items(tasksList.filter { !it.isHidden }) { task ->
                val mySub = submissionsList.find { it.taskId == task.id && it.employeeId == user?.employeeId }
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(task.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                    .background(
                                        when (mySub?.status) {
                                            "Approved" -> Color(0xFF10B981)
                                            "Rejected" -> Color(0xFFEF4444)
                                            "Pending Review" -> Color(0xFFFBBF24)
                                            else -> Color.DarkGray
                                        }
                                    ).padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(mySub?.status ?: "Belum Dikerjakan", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        Text("Category: ${task.contentCategory}", color = Color.LightGray, fontSize = 12.sp)
                        Text("Deskripsi: ${task.description}", color = Color.LightGray, fontSize = 12.sp)
                        Text("Instruksi: ${task.instructions}", color = Color.White, fontSize = 13.sp)

                        if (mySub?.status == "Rejected") {
                            Text("Alasan Penolakan: ${mySub.rejectionReason}", color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { 
                                    clipboard.setText(AnnotatedString(task.caption))
                                    Toast.makeText(context, "Sandi Caption dicopy!", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF475569)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Copy Caption", fontSize = 11.sp)
                            }
                            Button(
                                onClick = { 
                                    clipboard.setText(AnnotatedString(task.hashtags))
                                    Toast.makeText(context, "Hashtags dicopy!", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF475569)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Copy Hashtags", fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        if (mySub == null || mySub.status == "Rejected") {
                            Button(
                                onClick = { 
                                    viewModel.navigateTo(Screen.EmployeeSubmitProof)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Kirim Bukti Tugas", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- EMPLOYEE SUBMIT PROOF SCREEN ---
@Composable
fun EmployeeSubmitProofScreen(viewModel: ZuuViewModel) {
    val tasksList by viewModel.tasks.collectAsState()
    val user by viewModel.currentUser.collectAsState()
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var linkText by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("Instagram") }
    val context = LocalContext.current

    var selectedMaterials by remember { mutableStateOf(setOf<String>()) }
    var postingMode by remember { mutableStateOf("8_ig") } // "8_ig" or "5_ig_3_tt"
    var igVideoCount by remember { mutableStateOf(3) }
    var tiktokVideoCount by remember { mutableStateOf(3) }
    var confirmIgMinimum by remember { mutableStateOf(false) }
    var confirmTiktokMinimum by remember { mutableStateOf(false) }

    var proofScreenshotPath by remember { mutableStateOf("") }
    val proofLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = "proof_${selectedTask?.id ?: 0}_${System.currentTimeMillis()}.jpg"
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                proofScreenshotPath = file.absolutePath
                Toast.makeText(context, "Screenshot bukti berhasil dipilih dari galeri!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Gagal memproses gambar: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    EmployeeScaffold(viewModel = viewModel, title = "Daftar Tugas") {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Kirim Bukti Pekerjaan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)

            if (selectedTask == null) {
                Text("Pilih Tugas yang ingin disubmit:", color = Color.LightGray)
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(tasksList) { t ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                            modifier = Modifier.fillMaxWidth().clickable { selectedTask = t }
                        ) {
                            Text(t.title, color = Color.White, modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text("Pilihan: ${selectedTask!!.title}", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                    
                    item {
                        Text("Pilihan Bahan Video Konten (Wajib Pilih Minimal 3 Video):", color = ZuuAccentGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            val materials = listOf(
                                "Video Bahan 1: Reels / TikTok Viral A" to "Reels Live",
                                "Video Bahan 2: Reels / TikTok Viral B" to "Story Feed",
                                "Video Bahan 3: Story IG Promo Barbarian" to "Story IG",
                                "Video Bahan 4: Video TikTok Challenge Trend" to "Video TikTok",
                                "Video Bahan 5: Video TikTok Fun SquadBarBar365" to "Video TikTok"
                            )
                            materials.forEach { (name, type) ->
                                val isSelected = selectedMaterials.contains(name)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSelected) ZuuBorderPurple.copy(alpha = 0.3f) else ZuuCardPurple)
                                        .border(1.dp, if (isSelected) ZuuAccentGold else ZuuBorderPurple, RoundedCornerShape(10.dp))
                                        .clickable {
                                            selectedMaterials = if (isSelected) {
                                                selectedMaterials - name
                                            } else {
                                                selectedMaterials + name
                                            }
                                        }
                                        .padding(12.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.Add,
                                        contentDescription = null,
                                        tint = if (isSelected) ZuuAccentGold else Color.LightGray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(name, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                        Text("Kategori Bahan: $type", color = ZuuSoftLavender, fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text("Pilih Distribusi Publikasi Akun:", color = ZuuAccentGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                            // Option 1
                            val isOpt1 = postingMode == "8_ig"
                            Card(
                                onClick = { 
                                    postingMode = "8_ig" 
                                    platform = "Instagram"
                                },
                                colors = CardDefaults.cardColors(containerColor = if (isOpt1) ZuuBorderPurple.copy(alpha = 0.4f) else ZuuCardPurple),
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isOpt1) ZuuAccentGold else ZuuBorderPurple),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Share, null, tint = if (isOpt1) ZuuAccentGold else Color.LightGray)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("8 Akun IG", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("Post menggunakan 8 akun Instagram", color = ZuuSoftLavender, fontSize = 10.sp, textAlign = TextAlign.Center)
                                }
                            }

                            // Option 2
                            val isOpt2 = postingMode == "5_ig_3_tt"
                            Card(
                                onClick = { postingMode = "5_ig_3_tt" },
                                colors = CardDefaults.cardColors(containerColor = if (isOpt2) ZuuBorderPurple.copy(alpha = 0.4f) else ZuuCardPurple),
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isOpt2) ZuuAccentGold else ZuuBorderPurple),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.Check, null, tint = if (isOpt2) ZuuAccentGold else Color.LightGray)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("5 IG + 3 TikTok", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text("Post dengan 5 akun IG & 3 akun TikTok", color = ZuuSoftLavender, fontSize = 10.sp, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = ZuuCardPurple.copy(alpha = 0.5f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ZuuBorderPurple),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Konfirmasi Jumlah Unggahan Konten per Akun (Wajib Minimal 3 Video):", color = ZuuAccentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                
                                // Instagram Group (active under both modes)
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Instagram Reels & Story per Akun:", color = Color.White, fontSize = 12.sp)
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(
                                                onClick = { if (igVideoCount > 3) igVideoCount-- },
                                                modifier = Modifier.size(28.dp).background(ZuuBorderPurple, RoundedCornerShape(14.dp))
                                            ) {
                                                Icon(Icons.Default.KeyboardArrowDown, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                            }
                                            Text("$igVideoCount Video", color = ZuuAccentGold, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp), fontSize = 13.sp)
                                            IconButton(
                                                onClick = { igVideoCount++ },
                                                modifier = Modifier.size(28.dp).background(ZuuBorderPurple, RoundedCornerShape(14.dp))
                                            ) {
                                                Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { confirmIgMinimum = !confirmIgMinimum }) {
                                        Checkbox(
                                            checked = confirmIgMinimum,
                                            onCheckedChange = { confirmIgMinimum = it },
                                            colors = CheckboxDefaults.colors(checkedColor = ZuuAccentGold, uncheckedColor = ZuuBorderPurple)
                                        )
                                        Text("Saya mengonfirmasi bahwa setiap akun Instagram mengunggah minimal 3 video.", color = ZuuSoftLavender, fontSize = 10.sp)
                                    }
                                }

                                if (postingMode == "5_ig_3_tt") {
                                    HorizontalDivider(color = ZuuBorderPurple, modifier = Modifier.padding(vertical = 4.dp))
                                    // TikTok Group
                                    Column {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text("TikTok Video per Akun:", color = Color.White, fontSize = 12.sp)
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                IconButton(
                                                    onClick = { if (tiktokVideoCount > 3) tiktokVideoCount-- },
                                                    modifier = Modifier.size(28.dp).background(ZuuBorderPurple, RoundedCornerShape(14.dp))
                                                ) {
                                                    Icon(Icons.Default.KeyboardArrowDown, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                                }
                                                Text("$tiktokVideoCount Video", color = ZuuAccentGold, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp), fontSize = 13.sp)
                                                IconButton(
                                                    onClick = { tiktokVideoCount++ },
                                                    modifier = Modifier.size(28.dp).background(ZuuBorderPurple, RoundedCornerShape(14.dp))
                                                ) {
                                                    Icon(Icons.Default.Add, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { confirmTiktokMinimum = !confirmTiktokMinimum }) {
                                            Checkbox(
                                                checked = confirmTiktokMinimum,
                                                onCheckedChange = { confirmTiktokMinimum = it },
                                                colors = CheckboxDefaults.colors(checkedColor = ZuuAccentGold, uncheckedColor = ZuuBorderPurple)
                                            )
                                            Text("Saya mengonfirmasi bahwa setiap akun TikTok mengunggah minimal 3 video.", color = ZuuSoftLavender, fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text("Atur Platform Publikasi Utama:", color = Color.LightGray, fontSize = 13.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            listOf("Instagram", "TikTok").forEach { targetPr ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(selected = platform == targetPr, onClick = { platform = targetPr })
                                    Text(targetPr, color = Color.White, fontSize = 13.sp)
                                }
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = linkText, onValueChange = { linkText = it },
                            label = { Text("Tautan Link Postingan Video/Konten") },
                            textStyle = TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = ZuuAccentGold,
                                unfocusedBorderColor = ZuuBorderPurple,
                                focusedLabelColor = ZuuAccentGold,
                                unfocusedLabelColor = ZuuSoftLavender
                            )
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                        // Upload area
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(ZuuCardPurple)
                                .border(1.dp, Brush.linearGradient(listOf(ZuuBorderPurple, ZuuAccentGold.copy(alpha = 0.3f))), RoundedCornerShape(12.dp))
                                .clickable { proofLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            if (proofScreenshotPath.isNotEmpty()) {
                                AsyncImage(
                                    model = proofScreenshotPath,
                                    contentDescription = "Bukti Screenshot",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Ganti Screenshot Bukti (Galeri)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                                    Icon(Icons.Default.Add, null, tint = ZuuAccentGold, modifier = Modifier.size(32.dp))
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Unggah Bukti Screenshot Layar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text("Klik untuk memilih gambar dari galeri Anda", color = ZuuSoftLavender, fontSize = 11.sp)
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                if (selectedMaterials.size < 3) {
                                    Toast.makeText(context, "Mohon pilih minimal 3 video bahan konten!", Toast.LENGTH_LONG).show()
                                } else if (igVideoCount < 3) {
                                    Toast.makeText(context, "Minimal per akun Instagram harus upload 3 video reels dan story!", Toast.LENGTH_LONG).show()
                                } else if (!confirmIgMinimum) {
                                    Toast.makeText(context, "Silakan konfirmasi kepatuhan minimal upload 3 video Reels & Story di IG!", Toast.LENGTH_LONG).show()
                                } else if (postingMode == "5_ig_3_tt" && (tiktokVideoCount < 3)) {
                                    Toast.makeText(context, "Minimal per akun TikTok harus upload 3 video!", Toast.LENGTH_LONG).show()
                                } else if (postingMode == "5_ig_3_tt" && !confirmTiktokMinimum) {
                                    Toast.makeText(context, "Silakan konfirmasi kepatuhan minimal upload 3 video di TikTok!", Toast.LENGTH_LONG).show()
                                } else if (linkText.isEmpty()) {
                                    Toast.makeText(context, "Silakan isi tautan link pos Anda.", Toast.LENGTH_SHORT).show()
                                } else if (proofScreenshotPath.isEmpty()) {
                                    Toast.makeText(context, "Silakan unggah screenshot bukti terlebih dahulu.", Toast.LENGTH_SHORT).show()
                                } else {
                                    val distroStr = if (postingMode == "8_ig") "8 Akun IG" else "5 Akun IG + 3 TikTok"
                                    val finalPlatform = "$platform (Bahan: ${selectedMaterials.size} Video, IG: $igVideoCount reels/story, TT: ${if (postingMode == "5_ig_3_tt") tiktokVideoCount else 0} vids, Distro: $distroStr)"
                                    viewModel.submitTaskProof(
                                        selectedTask!!.id,
                                        selectedTask!!.title,
                                        finalPlatform,
                                        linkText,
                                        proofScreenshotPath
                                    )
                                    Toast.makeText(context, "Bukti berhasil disubmit!", Toast.LENGTH_SHORT).show()
                                    viewModel.navigateTo(Screen.EmployeeDashboard)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Kirim Sekarang", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

// --- EMPLOYEE CLAIM BONUS FYP ---
@Composable
fun EmployeeClaimFypScreen(viewModel: ZuuViewModel) {
    var lk by remember { mutableStateOf("") }
    var viewsInput by remember { mutableStateOf("") }
    var ndpScoreInput by remember { mutableStateOf("10") }
    val context = LocalContext.current

    var claimScreenshotPath by remember { mutableStateOf("") }
    val claimLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = "claim_fyp_${System.currentTimeMillis()}.jpg"
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val file = File(context.filesDir, fileName)
                val outputStream = FileOutputStream(file)
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                claimScreenshotPath = file.absolutePath
                Toast.makeText(context, "Screenshot insights berhasil dipilih dari galeri!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Gagal memproses gambar: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    EmployeeScaffold(viewModel = viewModel, title = "Klaim Bonus FYP") {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Pengajuan Klaim Bonus Video FYP", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("Bonus disesuaikan berdasarkan tayangan insights dan poin NDP mingguan.", fontSize = 12.sp, color = Color.LightGray)

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = lk, onValueChange = { lk = it }, label = { Text("Tautan Video (Instagram/TikTok/YT)") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())
            
            OutlinedTextField(value = viewsInput, onValueChange = { viewsInput = it }, label = { Text("Total Tayangan / Views Konten (Cth: 50000)") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())

            OutlinedTextField(value = ndpScoreInput, onValueChange = { ndpScoreInput = it }, label = { Text("Perkiraan NDP Pekan Ini") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())

            // Live Recommended Reward
            val views = viewsInput.toLongOrNull() ?: 0L
            val ndp = ndpScoreInput.toIntOrNull() ?: 0
            val recommended = viewModel.getFypRecommendedReward(views, ndp)

            Card(
                colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                border = androidx.compose.foundation.BorderStroke(1.dp, ZuuBorderPurple),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Rekomendasi Insentif Anda:", color = Color.LightGray, fontSize = 12.sp)
                    Text("Rp ${recommended.toInt()}", color = ZuuAccentGold, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                }
            }

            // Real Interactive Upload Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ZuuCardPurple)
                    .border(1.dp, Brush.linearGradient(listOf(ZuuBorderPurple, ZuuAccentGold.copy(alpha = 0.3f))), RoundedCornerShape(12.dp))
                    .clickable { claimLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (claimScreenshotPath.isNotEmpty()) {
                    AsyncImage(
                        model = claimScreenshotPath,
                        contentDescription = "Bukti Insights",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Ganti Screenshot Insights (Galeri)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Add, null, tint = ZuuAccentGold, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Unggah Bukti Screenshot Insights", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Klik untuk memilih gambar dari galeri Anda", color = ZuuSoftLavender, fontSize = 11.sp)
                    }
                }
            }

            Button(
                onClick = {
                    if (lk.isEmpty() || viewsInput.isEmpty()) {
                        Toast.makeText(context, "Mohon lengkapi seluruh rincian kuitansi.", Toast.LENGTH_SHORT).show()
                    } else if (claimScreenshotPath.isEmpty()) {
                        Toast.makeText(context, "Silakan unggah screenshot bukti insights terlebih dahulu.", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.claimFypBonus(lk, claimScreenshotPath, views, ndp)
                        Toast.makeText(context, "Klaim FYP diajukan! Menunggu validasi admin.", Toast.LENGTH_SHORT).show()
                        viewModel.navigateTo(Screen.EmployeeDashboard)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Kirim Ajuan Klaim", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

// --- EMPLOYEE PAYMENT HISTORY SCREEN ---
@Composable
fun EmployeePaymentScreen(viewModel: ZuuViewModel) {
    val paymentsList by viewModel.payments.collectAsState()
    val user by viewModel.currentUser.collectAsState()

    EmployeeScaffold(viewModel = viewModel, title = "Riwayat Pembayaran") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Riwayat Penggajian & Bonus Anda", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            val myPay = paymentsList.filter { it.employeeId == user?.employeeId }
            if (myPay.isEmpty()) {
                item {
                    Text("Belum ada mutasi keuangan tercatat.", color = Color.LightGray)
                }
            } else {
                items(myPay) { pay ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(pay.paymentType, color = Color.White, fontWeight = FontWeight.Bold)
                                Text("Tanggal ajukan: ${pay.dateString}", color = Color.LightGray, fontSize = 12.sp)
                                Text("Rp ${pay.amount}", color = Color(0xFF10B981), fontWeight = FontWeight.Bold)
                            }
                            Box(
                                modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                    .background(if (pay.status == "Paid") Color(0xFF10B981) else Color(0xFFFBBF24))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(pay.status, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- EMPLOYEE SUPPORT TICKETS SCREEN ---
@Composable
fun EmployeeSupportTicketsScreen(viewModel: ZuuViewModel) {
    val ticketsList by viewModel.tickets.collectAsState()
    val user by viewModel.currentUser.collectAsState()

    var showForm by remember { mutableStateOf(false) }
    var tTitle by remember { mutableStateOf("") }
    var tDesc by remember { mutableStateOf("") }

    val context = LocalContext.current

    EmployeeScaffold(viewModel = viewModel, title = "Dukungan Help") {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Konsultasi & Keluhan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Button(
                    onClick = { showForm = !showForm },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                ) {
                    Text(if (showForm) "Batal" else "Buat Tiket", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showForm) {
                OutlinedTextField(value = tTitle, onValueChange = { tTitle = it }, label = { Text("Judul Kendala") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = tDesc, onValueChange = { tDesc = it }, label = { Text("Deskripsi Detail Masalah") }, textStyle = TextStyle(color = Color.White), modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (tTitle.isNotEmpty() && tDesc.isNotEmpty()) {
                            viewModel.createSupportTicket(tTitle, tDesc)
                            tTitle = ""
                            tDesc = ""
                            showForm = false
                            Toast.makeText(context, "Tiket bantuan berhasil dikirim!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Kirim Tiket", fontWeight = FontWeight.Bold)
                }
            } else {
                val myTix = ticketsList.filter { it.employeeId == user?.employeeId }
                if (myTix.isEmpty()) {
                    Text("Belum ada tiket bantuan dikirim.", color = Color.LightGray)
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(myTix) { ticket ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(ticket.title, color = Color.White, fontWeight = FontWeight.Bold)
                                        Box(
                                            modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                                .background(if (ticket.status == "Open") Color(0xFF3B82F6) else Color(0xFF10B981))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(ticket.status, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Text(ticket.description, color = Color.LightGray, fontSize = 13.sp)
                                    if (ticket.reply.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text("Balasan Admin:", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Text(ticket.reply, color = Color.LightGray, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- EMPLOYEE PROFILE SCREEN ---
@Composable
fun EmployeeProfileScreen(viewModel: ZuuViewModel) {
    val user by viewModel.currentUser.collectAsState()
    val context = LocalContext.current

    // Local controller states initialized with current user info
    var fullName by remember { mutableStateOf(user?.fullName ?: "") }
    var whatsappNumber by remember { mutableStateOf(user?.whatsappNumber ?: "") }
    var telegramUsername by remember { mutableStateOf(user?.telegramUsername ?: "") }
    var bankName by remember { mutableStateOf(user?.bankName ?: "") }
    var bankAccountNumber by remember { mutableStateOf(user?.bankAccountNumber ?: "") }
    var bankAccountName by remember { mutableStateOf(user?.bankAccountName ?: "") }
    var profilePictureUrl by remember { mutableStateOf(user?.profilePictureUrl ?: "") }

    // Re-sync states if current user object changes (e.g. from viewModel side)
    LaunchedEffect(user) {
        user?.let {
            fullName = it.fullName
            whatsappNumber = it.whatsappNumber
            telegramUsername = it.telegramUsername
            bankName = it.bankName
            bankAccountNumber = it.bankAccountNumber
            bankAccountName = it.bankAccountName
            profilePictureUrl = it.profilePictureUrl
        }
    }

    // Avatar lists to easily pick a nice color gradient profile indicator if no URL is provided
    val gradientColors = listOf(
        Brush.linearGradient(listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))), // Blue
        Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))), // Purple
        Brush.linearGradient(listOf(Color(0xFF10B981), Color(0xFF047857))), // Emerald
        Brush.linearGradient(listOf(Color(0xFFF59E0B), Color(0xFFB45309))), // Amber
        Brush.linearGradient(listOf(Color(0xFFEF4444), Color(0xFFB91C1C))), // Red
        Brush.linearGradient(listOf(Color(0xFFEC4899), Color(0xFFBE185D)))  // Pink
    )

    // Select color index or custom image
    val avatarOptions = listOf(
        "⚡ Blue Lightning" to "avatar_blue",
        "🔮 Mystic Purple" to "avatar_purple",
        "🌿 Emerald Clover" to "avatar_emerald",
        "🔥 Amber Spark" to "avatar_amber",
        "🌌 Midnight Pink" to "avatar_pink"
    )

    EmployeeScaffold(viewModel = viewModel, title = "Profil Saya") {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // Profile Photo display
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(55.dp))
                        .background(
                            when (profilePictureUrl) {
                                "avatar_blue" -> gradientColors[0]
                                "avatar_purple" -> gradientColors[1]
                                "avatar_emerald" -> gradientColors[2]
                                "avatar_amber" -> gradientColors[3]
                                "avatar_pink" -> gradientColors[5]
                                else -> {
                                    if (profilePictureUrl.isEmpty()) {
                                        gradientColors[1] // default purple
                                    } else {
                                        Brush.linearGradient(listOf(Color(0xFF334155), Color(0xFF1E293B)))
                                    }
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (profilePictureUrl.isNotEmpty() &&
                        profilePictureUrl != "avatar_blue" &&
                        profilePictureUrl != "avatar_purple" &&
                        profilePictureUrl != "avatar_emerald" &&
                        profilePictureUrl != "avatar_amber" &&
                        profilePictureUrl != "avatar_pink"
                    ) {
                        AsyncImage(
                            model = profilePictureUrl,
                            contentDescription = "Foto Profil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    } else {
                        val emoji = when (profilePictureUrl) {
                            "avatar_blue" -> "⚡"
                            "avatar_purple" -> "🔮"
                            "avatar_emerald" -> "🌿"
                            "avatar_amber" -> "🔥"
                            "avatar_pink" -> "🌌"
                            else -> "👤"
                        }
                        Text(
                            text = emoji,
                            fontSize = 44.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = user?.fullName ?: "Nama Pengguna",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "ID Karyawan: ${user?.employeeId ?: ""}",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            // SECTION: Dompet Karyawan & Penarikan Dana (WD)
            item {
                val balance = user?.walletBalance ?: 0.0
                val registeredAt = user?.registeredAt ?: System.currentTimeMillis()
                val joinDay = viewModel.getJoinDayOfWeek(registeredAt)
                
                var showWdDialog by remember { mutableStateOf(false) }
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.5.dp, ZuuAccentGold.copy(alpha = 0.8f)),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Dompet Saya BarBar 💳",
                                    color = ZuuAccentGold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Hari Gajian Rutin: $joinDay",
                                    color = ZuuSoftLavender,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = ZuuAccentGold
                            )
                        }
                        
                        Text(
                            text = "Rp " + String.format("%,.0f", balance),
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp
                        )

                        Text(
                            text = "Akses penarikan mandiri 24/7. Seluruh transaksi gajian mingguan Anda diaudit dan masuk secara instan ke dompet digital Anda setiap hari *${joinDay}*.",
                            color = ZuuSoftLavender,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Button(
                            onClick = {
                                if (bankName.isBlank() || bankAccountNumber.isBlank() || bankAccountName.isBlank()) {
                                    Toast.makeText(context, "Lengkapi nama bank, nomor rekening dan pemilik di bagian bawah dulu!", Toast.LENGTH_LONG).show()
                                } else if (balance <= 0) {
                                    Toast.makeText(context, "Saldo dompet kosong!", Toast.LENGTH_SHORT).show()
                                } else {
                                    showWdDialog = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ZuuAccentGold,
                                contentColor = ZuuDeepPurpleBg
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.ShoppingCart, null, tint = ZuuDeepPurpleBg, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tarik Dana (Withdraw / WD)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
                
                if (showWdDialog) {
                    var wdAmountStr by remember { mutableStateOf("") }
                    AlertDialog(
                        onDismissRequest = { showWdDialog = false },
                        containerColor = ZuuCardPurple,
                        title = { Text("Tarik Dana Ke Rekening", color = Color.White, fontWeight = FontWeight.Bold) },
                        text = {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(
                                    text = "Saldo tersedia: Rp ${String.format("%,.0f", balance)}\n" +
                                            "Tujuan transfer: $bankName - $bankAccountNumber a/n $bankAccountName",
                                    color = ZuuSoftLavender,
                                    fontSize = 13.sp
                                )
                                OutlinedTextField(
                                    value = wdAmountStr,
                                    onValueChange = { wdAmountStr = it },
                                    label = { Text("Jumlah Penarikan (Rp)", color = ZuuAccentGold) },
                                    textStyle = TextStyle(color = Color.White),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = ZuuAccentGold,
                                        unfocusedBorderColor = ZuuBorderPurple
                                    )
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    val amount = wdAmountStr.toDoubleOrNull()
                                    if (amount == null || amount <= 0) {
                                        Toast.makeText(context, "Jumlah penarikan tidak valid!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        viewModel.requestWithdrawal(
                                            amount = amount,
                                            onError = { err ->
                                                (context as? android.app.Activity)?.runOnUiThread {
                                                    Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
                                                }
                                            },
                                            onSuccess = {
                                                (context as? android.app.Activity)?.runOnUiThread {
                                                    Toast.makeText(context, "Penarikan berhasil diajukan!", Toast.LENGTH_SHORT).show()
                                                }
                                                showWdDialog = false
                                            }
                                        )
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg)
                            ) {
                                Text("Konfirmasi WD", fontWeight = FontWeight.Bold)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showWdDialog = false }) {
                                Text("Batal", color = Color.White)
                            }
                        }
                    )
                }
            }

            // SECTION 1: Personal Contact Info
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Informasi Pribadi & Kontak",
                            color = ZuuAccentGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        HorizontalDivider(color = ZuuBorderPurple)

                        val fieldColors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = ZuuAccentGold,
                            unfocusedBorderColor = ZuuBorderPurple,
                            focusedLabelColor = ZuuAccentGold,
                            unfocusedLabelColor = ZuuSoftLavender
                        )

                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text("Nama Lengkap Anda") },
                            textStyle = TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            colors = fieldColors
                        )

                        OutlinedTextField(
                            value = whatsappNumber,
                            onValueChange = { whatsappNumber = it },
                            label = { Text("Nomor WhatsApp (Aktif)") },
                            textStyle = TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            colors = fieldColors
                        )

                        OutlinedTextField(
                            value = telegramUsername,
                            onValueChange = { telegramUsername = it },
                            label = { Text("Username Telegram (Contoh: @username)") },
                            textStyle = TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            colors = fieldColors
                        )
                    }
                }
            }

            // SECTION 2: Profile Photo Upload & Management
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Foto Profil Karyawan",
                            color = ZuuAccentGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        HorizontalDivider(color = ZuuBorderPurple)

                        Text(
                            text = "Gunakan foto asli atau gambar profesional Anda sebagai identitas utama di platform SquadBarBar365:",
                            color = ZuuSoftLavender,
                            fontSize = 12.sp
                        )

                        val fileLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetContent()
                        ) { uri: Uri? ->
                            uri?.let {
                                val fileName = "profile_${user?.employeeId ?: "custom"}_${System.currentTimeMillis()}.jpg"
                                try {
                                    val inputStream = context.contentResolver.openInputStream(it)
                                    val file = File(context.filesDir, fileName)
                                    val outputStream = FileOutputStream(file)
                                    inputStream?.use { input ->
                                        outputStream.use { output ->
                                            input.copyTo(output)
                                        }
                                    }
                                    profilePictureUrl = file.absolutePath
                                    Toast.makeText(context, "Foto profil berhasil dipilih!", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    Toast.makeText(context, "Gagal memproses gambar: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        val isCustomPhoto = profilePictureUrl.isNotEmpty() &&
                                !profilePictureUrl.startsWith("avatar_")

                        Button(
                            onClick = { fileLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ZuuBorderPurple,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isCustomPhoto) "Ganti Foto dari Galeri" else "Pilih Foto dari Galeri",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (isCustomPhoto) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ZuuDeepPurpleBg.copy(alpha = 0.5f))
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = ZuuAccentGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Foto Kustom Aktif",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "Hapus",
                                    color = Color(0xFFEF4444),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clickable {
                                            profilePictureUrl = ""
                                        }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // SECTION 3: Withdraw Payment Info (Bank / E-Wallet)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Metode Pencairan (Withdraw) Gaji & Bonus",
                            color = ZuuAccentGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        HorizontalDivider(color = ZuuBorderPurple)

                        Text(
                            text = "Silakan pilih salah satu Bank atau E-Wallet berikut untuk menerima transferan pendapatan mingguan & bonus FYP.",
                            color = ZuuSoftLavender,
                            fontSize = 12.sp
                        )

                        val popularBanksAndWallets = listOf(
                            "Bank BCA", "Bank Mandiri", "Bank BNI", "Bank BRI", "Bank CIMB Niaga", "Bank Jago",
                            "GoPay", "OVO", "DANA", "ShopeePay", "LinkAja"
                        )

                        var expandedBankSelect by remember { mutableStateOf(false) }

                        val walletFieldColors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = ZuuAccentGold,
                            unfocusedBorderColor = ZuuBorderPurple,
                            focusedLabelColor = ZuuAccentGold,
                            unfocusedLabelColor = ZuuSoftLavender
                        )

                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = bankName,
                                onValueChange = { bankName = it },
                                readOnly = true,
                                label = { Text("Pilih Nama Bank / E-Wallet") },
                                textStyle = TextStyle(color = Color.White),
                                trailingIcon = {
                                    IconButton(onClick = { expandedBankSelect = true }) {
                                        Icon(Icons.Default.ArrowDropDown, "Pilih", tint = ZuuSoftLavender)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = walletFieldColors
                            )
                            DropdownMenu(
                                expanded = expandedBankSelect,
                                onDismissRequest = { expandedBankSelect = false },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .background(ZuuCardPurple)
                            ) {
                                popularBanksAndWallets.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option, color = Color.White, fontWeight = FontWeight.Medium) },
                                        onClick = {
                                            bankName = option
                                            expandedBankSelect = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = bankAccountNumber,
                            onValueChange = { bankAccountNumber = it },
                            label = { Text("Nomor Rekening / Nomor Handphone E-Wallet") },
                            textStyle = TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            colors = walletFieldColors
                        )

                        OutlinedTextField(
                            value = bankAccountName,
                            onValueChange = { bankAccountName = it },
                            label = { Text("Nama Pemilik Rekening / Akun E-Wallet") },
                            textStyle = TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            colors = walletFieldColors
                        )
                    }
                }
            }

            // SECTION 3.5: Kemitraan Website BARBAR365 (Read-Only Info)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Kemitraan Website BARBAR365 🌐",
                            color = ZuuAccentGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        HorizontalDivider(color = ZuuBorderPurple)

                        Text(
                            text = "Status: Terverifikasi Aktif ✓",
                            color = Color(0xFF10B981),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )

                        Text(
                            text = "Data registrasi Anda di website https://ms001.barbar365a.site yang tersinkronisasi:",
                            color = ZuuSoftLavender,
                            fontSize = 12.sp
                        )

                        OutlinedTextField(
                            value = user?.ms001Username ?: "-",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("ID / Username BARBAR365") },
                            textStyle = TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = ZuuBorderPurple,
                                unfocusedBorderColor = ZuuBorderPurple,
                                focusedLabelColor = ZuuSoftLavender,
                                unfocusedLabelColor = ZuuSoftLavender
                            )
                        )

                        OutlinedTextField(
                            value = user?.ms001ReferralLink ?: "-",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Link Referral BARBAR365") },
                            textStyle = TextStyle(color = Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = ZuuBorderPurple,
                                unfocusedBorderColor = ZuuBorderPurple,
                                focusedLabelColor = ZuuSoftLavender,
                                unfocusedLabelColor = ZuuSoftLavender
                            )
                        )
                    }
                }
            }

            // SECTION 4: Action Button
            item {
                Button(
                    onClick = {
                        if (fullName.isEmpty() || whatsappNumber.isEmpty()) {
                            Toast.makeText(context, "Nama Lengkap dan WhatsApp wajib diisi!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.updateEmployeeProfile(
                                fullName = fullName,
                                whatsappNumber = whatsappNumber,
                                telegramUsername = telegramUsername,
                                bankName = bankName,
                                bankAccountNumber = bankAccountNumber,
                                bankAccountName = bankAccountName,
                                profilePictureUrl = profilePictureUrl
                            )
                            Toast.makeText(context, "Profil anda berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg)
                ) {
                    Icon(Icons.Default.Check, null, tint = ZuuDeepPurpleBg)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Simpan Perubahan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// --- ADMIN SUPPORT TICKETS REVIEW SCREEN ---
@Composable
fun AdminSupportTicketsScreen(viewModel: ZuuViewModel) {
    // Admin support logs placeholder, can easily review tickets
}

// --- ADMIN NDP TRACKING CONTROL SCREEN ---
@Composable
fun AdminNDPTrackingScreen(viewModel: ZuuViewModel) {
    // Poin NDP setting placeholder
}

// --- EMPLOYEE ANNOUNCEMENTS SCREEN ---
@Composable
fun EmployeeAnnouncementsScreen(viewModel: ZuuViewModel) {
}

// ==========================================
// ============= OWNER SCREENS ==============
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerDrawerScaffold(viewModel: ZuuViewModel, title: String, content: @Composable () -> Unit) {
    val navItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = ZuuAccentGold,
        selectedTextColor = ZuuAccentGold,
        unselectedIconColor = ZuuSoftLavender,
        unselectedTextColor = ZuuSoftLavender,
        indicatorColor = ZuuBorderPurple
    )

    Box(modifier = Modifier.fillMaxSize().background(ZuuDeepPurpleBg)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ZuuDarkAppBar),
                actions = {
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(Icons.Default.ExitToApp, "Logout", tint = Color.White)
                    }
                }
            )
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
            NavigationBar(containerColor = ZuuDarkAppBar) {
                NavigationBarItem(
                    selected = title == "Owner Dashboard",
                    onClick = { viewModel.navigateTo(Screen.OwnerDashboard) },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Dashboard", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Kelola Admin",
                    onClick = { viewModel.navigateTo(Screen.OwnerAdminManagement) },
                    icon = { Icon(Icons.Default.Build, null) },
                    label = { Text("Kelola Admin", fontSize = 10.sp) },
                    colors = navItemColors
                )
                NavigationBarItem(
                    selected = title == "Ikhtisar Keuangan",
                    onClick = { viewModel.navigateTo(Screen.OwnerFinancialOverview) },
                    icon = { Icon(Icons.Default.Star, null) },
                    label = { Text("Keuangan", fontSize = 10.sp) },
                    colors = navItemColors
                )
            }
        }
    }
}

@Composable
fun OwnerDashboardScreen(viewModel: ZuuViewModel) {
    val usersList by viewModel.users.collectAsState()
    val submissionsList by viewModel.submissions.collectAsState()
    val paymentsList by viewModel.payments.collectAsState()
    val logsList by viewModel.logs.collectAsState()
    val ndpRecords by viewModel.ndpRecords.collectAsState()

    // Counts
    val totalEmployees = usersList.count { it.role == "Employee" }
    val totalAdmins = usersList.count { it.role == "Admin" }
    val activeEmployees = usersList.count { it.role == "Employee" && it.status == "Active" }
    val pendingEmployees = usersList.count { it.role == "Employee" && it.status == "Pending" }

    // Financial calculations
    val totalSalaryWeekly = paymentsList.filter { it.paymentType == "Weekly Salary" && it.status == "Paid" }.sumOf { it.amount }
    val totalBonusPaid = paymentsList.filter { (it.paymentType == "FYP Bonus" || it.paymentType == "NDP Bonus" || it.paymentType == "Referral Reward") && it.status == "Paid" }.sumOf { it.amount }
    val totalNdpScores = if (ndpRecords.isNotEmpty()) ndpRecords.sumOf { it.ndpScore } else 0
    val avgNdp = if (ndpRecords.isNotEmpty()) totalNdpScores.toDouble() / ndpRecords.size else 0.0

    // Top Employees: Group submissions by employeeId, count approved
    val topEmployees = usersList.filter { it.role == "Employee" }.map { emp ->
        val approvedSubCount = submissionsList.count { it.employeeId == emp.employeeId && it.status == "Approved" }
        val empNdpList = ndpRecords.filter { it.employeeId == emp.employeeId }
        val maxNdp = if (empNdpList.isNotEmpty()) empNdpList.maxOf { it.ndpScore } else 0
        emp to (approvedSubCount to maxNdp)
    }.sortedByDescending { it.second.first }.take(5)

    // Admin / System Activities: filtering logs
    val adminActivityLogs = logsList.sortedByDescending { it.timestamp }.take(10)

    OwnerDrawerScaffold(viewModel = viewModel, title = "Owner Dashboard") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Executive Welcome Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1D0B33)),
                    border = BorderStroke(1.dp, ZuuBorderPurple),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ZuuAccentGold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Star, null, tint = ZuuAccentGold, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Panel Direksi Utama", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ZuuAccentGold)
                                Text("ZUU GROUP MANAGEMENT", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = ZuuSoftLavender)
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Selamat datang kembali, Owner! Pantau produktivitas operasional tim harian, kas keluar perusahaan, serta otorisasi tim administrator.",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // High Fidelity Realtime Data Grid
            item {
                Column {
                    Text("Matrik Kinerja & Distribusi Tim", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminCardStat("Kas Gaji (Paid)", "Rp " + String.format("%,.0f", totalSalaryWeekly), Modifier.weight(1.2f), Color(0xFF8B5CF6))
                        AdminCardStat("Rata-rata NDP", String.format("%.1f NDP", avgNdp), Modifier.weight(0.8f), Color(0xFFEC4899))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminCardStat("Karyawan Terdaftar", "$totalEmployees ($activeEmployees Aktif)", Modifier.weight(1f), Color(0xFF3B82F6))
                        AdminCardStat("Para Admin Aktif", "$totalAdmins Admin", Modifier.weight(1f), Color(0xFF10B981))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminCardStat("Registrasi Pending", "$pendingEmployees Calon", Modifier.weight(1f), Color(0xFFFBBF24))
                        AdminCardStat("Kas Bonus (Paid)", "Rp " + String.format("%,.0f", totalBonusPaid), Modifier.weight(1.1f), Color(0xFF10B981))
                    }
                }
            }

            // Top Employees Widget
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                    border = BorderStroke(1.dp, ZuuBorderPurple),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Top 5 Karyawan Berkinerja Terbaik", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ZuuAccentGold)
                            Icon(Icons.Default.Star, null, tint = ZuuAccentGold, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        if (topEmployees.isEmpty()) {
                            Text("Belum ada data kinerja karyawan yang disetujui.", fontSize = 12.sp, color = Color.Gray)
                        } else {
                            topEmployees.forEachIndexed { index, (emp, stats) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (index == 0) ZuuAccentGold.copy(alpha = 0.2f)
                                                    else Color.White.copy(alpha = 0.08f)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "${index + 1}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (index == 0) ZuuAccentGold else Color.White
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(emp.fullName, fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                                            Text("ID: ${emp.employeeId}", fontSize = 11.sp, color = ZuuSoftLavender)
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(ZuuBorderPurple.copy(alpha = 0.4f))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "${stats.first} ACC | NDP Max ${stats.second}",
                                            fontSize = 10.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                if (index < topEmployees.size - 1) {
                                    Divider(color = Color.White.copy(alpha = 0.05f))
                                }
                            }
                        }
                    }
                }
            }

            // SECTION: Pusat Kendali Administrasi (Admin Hub)
            /* item {
                Text(
                    text = "Pusat Kendali Administrasi (Admin Hub) 💻", 
                    fontSize = 15.sp, 
                    fontWeight = FontWeight.Bold, 
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                     shape = RoundedCornerShape(16.dp),
                     colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                     border = BorderStroke(1.dp, ZuuBorderPurple),
                     modifier = Modifier.fillMaxWidth()
                ) {
                     Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                         Text(
                             text = "Gunakan akses direktur Anda untuk menjalankan seluruh fungsi dan fitur operasional Administrator:",
                             fontSize = 12.sp,
                             color = ZuuSoftLavender
                         )
                         
                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.spacedBy(10.dp)
                         ) {
                             AdminCommandButton(
                                 title = "Dashboard Admin",
                                 icon = Icons.Default.Home,
                                 color = Color(0xFF3B82F6),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminDashboard) }
                             )
                             AdminCommandButton(
                                 title = "Kelola Karyawan",
                                 icon = Icons.Default.Person,
                                 color = Color(0xFF10B981),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminEmployeesList) }
                             )
                         }
                         
                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.spacedBy(10.dp)
                         ) {
                             AdminCommandButton(
                                 title = "Buat Tugas",
                                 icon = Icons.Default.Add,
                                 color = Color(0xFF8B5CF6),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminCreateTask) }
                             )
                             AdminCommandButton(
                                 title = "Review Bukti",
                                 icon = Icons.Default.Edit,
                                 color = Color(0xFFEC4899),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminReviewSubmissions) }
                             )
                         }
                         
                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.spacedBy(10.dp)
                         ) {
                             AdminCommandButton(
                                 title = "Review Klaim",
                                 icon = Icons.Default.Check,
                                 color = Color(0xFFF59E0B),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminReviewBonusClaims) }
                             )
                             AdminCommandButton(
                                 title = "Keuangan & Gaji",
                                 icon = Icons.Default.ShoppingCart,
                                 color = Color(0xFF10B981),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminReviewPayments) }
                             )
                         }

                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.spacedBy(10.dp)
                         ) {
                             AdminCommandButton(
                                 title = "Aduan / Ticket",
                                 icon = Icons.Default.Send,
                                 color = Color(0xFFEF4444),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminSupportTickets) }
                             )
                             AdminCommandButton(
                                 title = "Tracking NDP",
                                 icon = Icons.Default.Star,
                                 color = Color(0xFF10B981),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminNDPTracking) }
                             )
                         }

                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.spacedBy(10.dp)
                         ) {
                             AdminCommandButton(
                                 title = "Kalender Konten",
                                 icon = Icons.Default.DateRange,
                                 color = Color(0xFFFBBF24),
                                 modifier = Modifier.weight(1f),
                                 onClick = { viewModel.navigateTo(Screen.AdminContentCalendar) }
                             )
                             Spacer(modifier = Modifier.weight(1f))
                         }

                         Button(
                             onClick = { viewModel.navigateTo(Screen.AdminConfigSettings) },
                             colors = ButtonDefaults.buttonColors(containerColor = ZuuBorderPurple, contentColor = Color.White),
                             shape = RoundedCornerShape(10.dp),
                             modifier = Modifier.fillMaxWidth().height(40.dp)
                         ) {
                             Icon(Icons.Default.Settings, null, tint = Color.White, modifier = Modifier.size(16.dp))
                             Spacer(modifier = Modifier.width(6.dp))
                             Text("Buka Pengukuran & Setelan Hub", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                         }
                     }
                 }
             }

            */ // Recent Owner and Admin Activities Widget
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                    border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.7f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Log Audit Administratif Terbaru", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981))
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        if (adminActivityLogs.isEmpty()) {
                            Text("Belum ada rincian riwayat log tindakan.", fontSize = 12.sp, color = Color.Gray)
                        } else {
                            adminActivityLogs.forEach { log ->
                                val date = SimpleDateFormat("dd/MM HH:mm:ss", Locale.getDefault()).format(Date(log.timestamp))
                                Column(modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth()) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                        Text(log.action, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ZuuAccentGold)
                                        Text(date, fontSize = 10.sp, color = Color.Gray)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text("Aksi oleh [${log.employeeId}] - ${log.details}", fontSize = 11.sp, color = Color.LightGray)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Divider(color = Color.White.copy(alpha = 0.05f))
                                }
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerAdminManagementScreen(viewModel: ZuuViewModel) {
    val usersList by viewModel.users.collectAsState()
    val logsList by viewModel.logs.collectAsState()
    val context = LocalContext.current

    val admins = usersList.filter { it.role == "Admin" }

    var searchNameQuery by remember { mutableStateOf("") }
    var showCreateDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<User?>(null) }
    var showActivityDialog by remember { mutableStateOf<User?>(null) }

    // Dialog form states
    var fullNameForm by remember { mutableStateOf("") }
    var waForm by remember { mutableStateOf("") }
    var passwordForm by remember { mutableStateOf("") }

    val filteredAdmins = admins.filter {
        it.fullName.contains(searchNameQuery, ignoreCase = true) ||
        it.employeeId.contains(searchNameQuery, ignoreCase = true) ||
        it.whatsappNumber.contains(searchNameQuery, ignoreCase = true)
    }

    OwnerDrawerScaffold(viewModel = viewModel, title = "Kelola Admin") {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Daftar Administrator", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Button(
                        onClick = {
                            fullNameForm = ""
                            waForm = ""
                            passwordForm = ""
                            showCreateDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg)
                    ) {
                        Icon(Icons.Default.Add, null, tint = ZuuDeepPurpleBg)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Tambah Admin", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Filter Bar
                OutlinedTextField(
                    value = searchNameQuery,
                    onValueChange = { searchNameQuery = it },
                    placeholder = { Text("Cari Admin berdasar Nama / ID / No WA...", color = Color.Gray, fontSize = 12.sp) },
                    textStyle = TextStyle(color = Color.White, fontSize = 13.sp),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = ZuuSoftLavender) },
                    trailingIcon = {
                        if (searchNameQuery.isNotEmpty()) {
                            IconButton(onClick = { searchNameQuery = "" }) {
                                Icon(Icons.Default.Refresh, null, tint = Color.White)
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF15072B),
                        unfocusedContainerColor = Color(0xFF15072B),
                        focusedBorderColor = ZuuAccentGold,
                        unfocusedBorderColor = ZuuBorderPurple
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (filteredAdmins.isEmpty()) {
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Belum ada admin yang cocok.", color = Color.LightGray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredAdmins) { admin ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(admin.fullName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                            Text("ID Admin: ${admin.employeeId}", fontSize = 12.sp, color = ZuuSoftLavender)
                                            Text("WhatsApp: ${admin.whatsappNumber}", fontSize = 12.sp, color = Color.LightGray)
                                            Text("Kata Sandi: ${admin.passwordHash}", fontSize = 12.sp, color = Color.LightGray)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(if (admin.status == "Active") Color(0xFF10B981) else Color(0xFFEF4444))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text(admin.status, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Divider(color = Color.White.copy(alpha = 0.08f), thickness = 0.5.dp)
                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                fullNameForm = admin.fullName
                                                waForm = admin.whatsappNumber
                                                passwordForm = admin.passwordHash
                                                showEditDialog = admin
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF475569)),
                                            modifier = Modifier.weight(1f).height(36.dp),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Icon(Icons.Default.Edit, null, tint = Color.White, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Ubah", fontSize = 11.sp, color = Color.White)
                                        }

                                        Button(
                                            onClick = {
                                                showActivityDialog = admin
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                                            modifier = Modifier.weight(1.2f).height(36.dp),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Icon(Icons.Default.List, null, tint = Color.White, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Log Aktivitas", fontSize = 11.sp, color = Color.White)
                                        }

                                        Button(
                                            onClick = {
                                                val nextStatus = if (admin.status == "Active") "Suspended" else "Active"
                                                viewModel.ownerUpdateAdmin(admin, admin.fullName, admin.whatsappNumber, admin.passwordHash, nextStatus) { ok, err ->
                                                    if (ok) {
                                                        Toast.makeText(context, "Status admin sukses disesuaikan!", Toast.LENGTH_SHORT).show()
                                                    } else {
                                                        Toast.makeText(context, err ?: "Gagal membekukan admin", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = if (admin.status == "Active") Color(0xFFEF4444) else Color(0xFF10B981)),
                                            modifier = Modifier.weight(1f).height(36.dp),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Text(if (admin.status == "Active") "Bekukan" else "Aktifkan", fontSize = 11.sp, color = Color.White)
                                        }

                                        Button(
                                            onClick = {
                                                viewModel.ownerDeleteAdmin(admin) { ok ->
                                                    Toast.makeText(context, "Admin berhasil dihapus!", Toast.LENGTH_SHORT).show()
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                            modifier = Modifier.height(36.dp),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Icon(Icons.Default.Delete, null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // MODAL DIALOGS

            // 1. CREATE ADMIN DIALOG
            if (showCreateDialog) {
                AlertDialog(
                    onDismissRequest = { showCreateDialog = false },
                    containerColor = ZuuCardPurple,
                    title = { Text("Tambah Admin Baru", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedTextField(
                                value = fullNameForm,
                                onValueChange = { fullNameForm = it },
                                label = { Text("Nama Lengkap Admin") },
                                textStyle = TextStyle(color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ZuuAccentGold,
                                    unfocusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.LightGray,
                                    focusedLabelColor = ZuuAccentGold
                                )
                            )
                            OutlinedTextField(
                                value = waForm,
                                onValueChange = { waForm = it },
                                label = { Text("Nomor WhatsApp (Aktif)") },
                                textStyle = TextStyle(color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ZuuAccentGold,
                                    unfocusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.LightGray,
                                    focusedLabelColor = ZuuAccentGold
                                )
                            )
                            OutlinedTextField(
                                value = passwordForm,
                                onValueChange = { passwordForm = it },
                                label = { Text("Password Akun") },
                                textStyle = TextStyle(color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ZuuAccentGold,
                                    unfocusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.LightGray,
                                    focusedLabelColor = ZuuAccentGold
                                )
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (fullNameForm.isEmpty() || waForm.isEmpty() || passwordForm.isEmpty()) {
                                    Toast.makeText(context, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                viewModel.ownerCreateAdmin(fullNameForm, waForm, passwordForm) { ok, err ->
                                    if (ok) {
                                        showCreateDialog = false
                                        Toast.makeText(context, "Admin baru berhasil dibuat!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, err ?: "Pendaftaran admin gagal", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg)
                        ) {
                            Text("Simpan", fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showCreateDialog = false }) {
                            Text("Batal", color = Color.White)
                        }
                    }
                )
            }

            // 2. EDIT ADMIN DIALOG
            showEditDialog?.let { currentAdmin ->
                AlertDialog(
                    onDismissRequest = { showEditDialog = null },
                    containerColor = ZuuCardPurple,
                    title = { Text("Ubah Rincian Admin", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            OutlinedTextField(
                                value = fullNameForm,
                                onValueChange = { fullNameForm = it },
                                label = { Text("Nama Lengkap Admin") },
                                textStyle = TextStyle(color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ZuuAccentGold,
                                    unfocusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.LightGray,
                                    focusedLabelColor = ZuuAccentGold
                                )
                            )
                            OutlinedTextField(
                                value = waForm,
                                onValueChange = { waForm = it },
                                label = { Text("Nomor WhatsApp") },
                                textStyle = TextStyle(color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ZuuAccentGold,
                                    unfocusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.LightGray,
                                    focusedLabelColor = ZuuAccentGold
                                )
                            )
                            OutlinedTextField(
                                value = passwordForm,
                                onValueChange = { passwordForm = it },
                                label = { Text("Password Akun") },
                                textStyle = TextStyle(color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ZuuAccentGold,
                                    unfocusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.LightGray,
                                    focusedLabelColor = ZuuAccentGold
                                )
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (fullNameForm.isEmpty() || waForm.isEmpty() || passwordForm.isEmpty()) {
                                    Toast.makeText(context, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                viewModel.ownerUpdateAdmin(currentAdmin, fullNameForm, waForm, passwordForm, currentAdmin.status) { ok, err ->
                                    if (ok) {
                                        showEditDialog = null
                                        Toast.makeText(context, "Data Admin berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, err ?: "Pembaruan admin gagal", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg)
                        ) {
                            Text("Simpan", fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showEditDialog = null }) {
                            Text("Batal", color = Color.White)
                        }
                    }
                )
            }

            // 3. ACTIVITY LOGS DIALOG
            showActivityDialog?.let { currentAdmin ->
                val adminLogs = logsList.filter { it.employeeId == currentAdmin.employeeId }.sortedByDescending { it.timestamp }
                AlertDialog(
                    onDismissRequest = { showActivityDialog = null },
                    containerColor = ZuuCardPurple,
                    title = { Text("Log Aktivitas: ${currentAdmin.fullName}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                    text = {
                        Box(modifier = Modifier.sizeIn(maxHeight = 350.dp, maxWidth = 300.dp)) {
                            if (adminLogs.isEmpty()) {
                                Text("Admin ini belum memiliki riwayat aksi.", color = Color.LightGray, fontSize = 12.sp)
                            } else {
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(adminLogs) { log ->
                                        val date = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(Date(log.timestamp))
                                        Column {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(log.action, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = ZuuAccentGold)
                                                Text(date, fontSize = 9.sp, color = Color.Gray)
                                            }
                                            Text(log.details, fontSize = 11.sp, color = Color.LightGray)
                                            Divider(color = Color.White.copy(alpha = 0.08f), modifier = Modifier.padding(top = 4.dp))
                                        }
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showActivityDialog = null },
                            colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg)
                        ) {
                            Text("Tutup", fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun OwnerFinancialOverviewScreen(viewModel: ZuuViewModel) {
    val paymentsList by viewModel.payments.collectAsState()

    // Salary Paid
    val totalSalaryPaid = paymentsList.filter { it.paymentType == "Weekly Salary" && it.status == "Paid" }.sumOf { it.amount }
    
    // Bonuses Paid
    val totalBonusesPaid = paymentsList.filter { (it.paymentType == "FYP Bonus" || it.paymentType == "NDP Bonus") && it.status == "Paid" }.sumOf { it.amount }
    
    // Total NDP bonus
    val totalNdpBonusPaid = paymentsList.filter { it.paymentType == "NDP Bonus" && it.status == "Paid" }.sumOf { it.amount }
    
    // Total Referral bonus
    val totalReferralPaid = paymentsList.filter { it.paymentType == "Referral Reward" && it.status == "Paid" }.sumOf { it.amount }

    // Estimate active periods expenses
    val monthlyExpenses = paymentsList.filter { it.status == "Paid" }.sumOf { it.amount }
    val weeklyExpenses = paymentsList.filter { it.status == "Paid" && (it.dateString.contains("Minggu") || it.dateString.contains("-")) }.take(10).sumOf { it.amount }

    OwnerDrawerScaffold(viewModel = viewModel, title = "Ikhtisar Keuangan") {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Evaluasi Biaya & Transaksi Perusahaan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Laporan neraca pengeluaran kas kelompok lunas.", fontSize = 12.sp, color = ZuuSoftLavender)
            }

            // Summary grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminCardStat("Gaji Pokok Terbayar", "Rp " + String.format("%,.0f", totalSalaryPaid), Modifier.weight(1f), Color(0xFF3B82F6))
                        AdminCardStat("Total Bonus Terbayar", "Rp " + String.format("%,.0f", totalBonusesPaid), Modifier.weight(1f), Color(0xFF10B981))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminCardStat("Pekanan NDP Reward", "Rp " + String.format("%,.0f", totalNdpBonusPaid), Modifier.weight(1f), Color(0xFFFBBF24))
                        AdminCardStat("Klaim Afiliasi/Referral", "Rp " + String.format("%,.0f", totalReferralPaid), Modifier.weight(1f), Color(0xFFEC4899))
                    }
                }
            }

            // Expense widgets
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                    border = BorderStroke(1.dp, ZuuBorderPurple),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Metrik Pengeluaran Kas", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Pengeluaran Bulanan (Periode Aktif):", fontSize = 12.sp, color = Color.LightGray)
                            Text("Rp " + String.format("%,.0f", monthlyExpenses), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ZuuAccentGold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Estimasi Biaya Pekan Ini:", fontSize = 12.sp, color = Color.LightGray)
                            Text("Rp " + String.format("%,.0f", weeklyExpenses), fontSize = 13.sp, fontWeight = FontWeight.Bold, color = ZuuSoftLavender)
                        }
                    }
                }
            }

            // Real Interactive Canvas Graph
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                    border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.8f)),
                    modifier = Modifier.fillMaxWidth().height(260.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Histori & Tren Pengeluaran Ril Lunas", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Representasi grafis kurva dari 7 transaksi lunas terakhir", fontSize = 11.sp, color = Color.LightGray)
                        Spacer(modifier = Modifier.height(18.dp))
                        
                        val chartPayments = paymentsList.filter { it.status == "Paid" }.sortedBy { it.id }.takeLast(7)
                        
                        if (chartPayments.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Belum ada data transaksi lunas untuk ditampilkan.", fontSize = 12.sp, color = Color.Gray)
                            }
                        } else {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val points = chartPayments.map { it.amount.toFloat() }
                                val maxVal = (points.maxOrNull() ?: 100000f).coerceAtLeast(100000f) * 1.25f
                                val width = size.width
                                val height = size.height
                                val spacing = if (points.size > 1) width / (points.size - 1) else width
                                
                                for (i in 1..4) {
                                    val y = height * i / 5
                                    drawLine(Color.White.copy(alpha = 0.08f), Offset(0f, y), Offset(width, y), 1.dp.toPx())
                                }

                                if (points.size == 1) {
                                    val cy = height - (points[0] / maxVal) * height
                                    drawCircle(ZuuAccentGold, 6.dp.toPx(), Offset(width / 2, cy))
                                } else {
                                    for (i in 0 until points.size - 1) {
                                        val x1 = i * spacing
                                        val y1 = height - (points[i] / maxVal) * height
                                        val x2 = (i + 1) * spacing
                                        val y2 = height - (points[i + 1] / maxVal) * height
                                        drawLine(ZuuAccentGold, Offset(x1, y1), Offset(x2, y2), strokeWidth = 3.dp.toPx())
                                        drawCircle(ZuuLightGold, 5.dp.toPx(), Offset(x1, y1))
                                    }
                                    drawCircle(ZuuLightGold, 5.dp.toPx(), Offset(width, height - (points.last() / maxVal) * height))
                                }
                            }
                        }
                    }
                }
            }

            // List of Recent Financial Payments
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF15072B)),
                    border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Buku Pengeluaran Lunas Terkini (Maks 10)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))
                        val paidPayments = paymentsList.filter { it.status == "Paid" }.sortedByDescending { it.id }.take(10)
                        if (paidPayments.isEmpty()) {
                            Text("Belum ada pencatatan transaksi kas lunas.", fontSize = 12.sp, color = Color.Gray)
                        } else {
                            paidPayments.forEachIndexed { idx, pay ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(pay.employeeName, fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                                        Text("${pay.paymentType} • ID: ${pay.employeeId} • ${pay.dateString}", fontSize = 11.sp, color = Color.LightGray)
                                    }
                                    Text("Rp " + String.format("%,.0f", pay.amount), fontSize = 13.sp, color = Color(0xFF10B981), fontWeight = FontWeight.ExtraBold)
                                }
                                if (idx < paidPayments.size - 1) {
                                    Divider(color = Color.White.copy(alpha = 0.05f))
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

// --- SERVICE ENGINEER DASHBOARD SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceEngineerDashboardScreen(viewModel: ZuuViewModel) {
    val users by viewModel.users.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val submissions by viewModel.submissions.collectAsState()
    val tickets by viewModel.tickets.collectAsState()
    val logs by viewModel.logs.collectAsState()
    val isSyncing by viewModel.isSyncing.collectAsState()
    val syncErrorMessage by viewModel.syncErrorMessage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var supabaseUrlForm by remember { mutableStateOf(viewModel.supabaseClient.supabaseUrl) }
    var supabaseKeyForm by remember { mutableStateOf(viewModel.supabaseClient.supabaseKey) }
    var testResultMsg by remember { mutableStateOf<String?>(null) }
    var testSuccess by remember { mutableStateOf(false) }
    var showSqlSchema by remember { mutableStateOf(false) }

    // Selected user for editing status & role
    var selectedUserForEdit by remember { mutableStateOf<User?>(null) }

    val filteredUsers = users.filter {
        it.fullName.contains(searchQuery, ignoreCase = true) ||
        it.employeeId.contains(searchQuery, ignoreCase = true) ||
        it.whatsappNumber.contains(searchQuery, ignoreCase = true) ||
        it.role.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Barbar Engine Hub 🛠️", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ZuuDarkAppBar),
                actions = {
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Log Out", tint = Color.White)
                    }
                }
            )
        },
        containerColor = ZuuDeepPurpleBg
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                    border = BorderStroke(1.dp, Brush.linearGradient(listOf(ZuuBorderPurple, ZuuAccentGold.copy(alpha = 0.4f))))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Build, null, tint = ZuuAccentGold, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                val me = viewModel.currentUser.collectAsState()
                                Text("Layanan Service Engineer", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("Nama: ${me.value?.fullName ?: "Barbar"} • ID: ${me.value?.employeeId ?: "ENG-001"}", color = ZuuSoftLavender, fontSize = 12.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "Selamat datang di Pusat Kendali Integrasi Supabase dan Manajemen Data Engine. Anda memiliki kewenangan memantau database lokal Room, mengonfigurasi jalur REST Supabase, mensinkronisasikan backend, dan memodifikasi profil otoritas pengguna.",
                            fontSize = 11.sp,
                            color = ZuuSoftLavender.copy(alpha = 0.85f),
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Quick Counter Grid
            item {
                Text("Kesehatan Sistem & Metrik database 📊", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Users Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF160935)),
                        border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Total Sesi User", fontSize = 10.sp, color = ZuuSoftLavender)
                            Text("${users.size}", fontSize = 24.sp, color = ZuuAccentGold, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                    // Tasks Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF160935)),
                        border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Tugas Sistem", fontSize = 10.sp, color = ZuuSoftLavender)
                            Text("${tasks.size}", fontSize = 24.sp, color = Color(0xFF60A5FA), fontWeight = FontWeight.ExtraBold)
                        }
                    }
                    // Submissions Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF160935)),
                        border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Rincian Bukti", fontSize = 10.sp, color = ZuuSoftLavender)
                            Text("${submissions.size}", fontSize = 24.sp, color = Color(0xFF34D399), fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            }

            // Supabase integration panel
            item {
                Text("Pengaturan Integrasi Database Supabase ⚡", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                    border = BorderStroke(1.dp, ZuuBorderPurple)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(
                                        if (viewModel.supabaseClient.isConfigured) Color(0xFF10B981) else Color(0xFFEF4444),
                                        CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (viewModel.supabaseClient.isConfigured) "Status: Terintegrasi dengan Cloud Supabase" else "Status: Mode Lokal Offline (Room)",
                                color = if (viewModel.supabaseClient.isConfigured) Color(0xFF10B981) else Color(0xFFEC4899),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedTextField(
                            value = supabaseUrlForm,
                            onValueChange = { supabaseUrlForm = it },
                            label = { Text("Supabase URL API") },
                            placeholder = { Text("https://xxx.supabase.co") },
                            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ZuuAccentGold,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = ZuuAccentGold,
                                unfocusedLabelColor = Color.LightGray
                            )
                        )

                        OutlinedTextField(
                            value = supabaseKeyForm,
                            onValueChange = { supabaseKeyForm = it },
                            label = { Text("Supabase Service / Anon API Key") },
                            placeholder = { Text("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") },
                            textStyle = TextStyle(color = Color.White, fontSize = 11.sp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ZuuAccentGold,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = ZuuAccentGold,
                                unfocusedLabelColor = Color.LightGray
                            )
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(
                                onClick = {
                                    viewModel.supabaseClient.supabaseUrl = supabaseUrlForm
                                    viewModel.supabaseClient.supabaseKey = supabaseKeyForm
                                    
                                    if (supabaseUrlForm.isNotEmpty() && supabaseKeyForm.isNotEmpty()) {
                                        testResultMsg = "Konfigurasi Supabase disimpan dan diaktifkan sukses."
                                        testSuccess = true
                                    } else {
                                        testResultMsg = "URL & API Key direset ke kosong. Berjalan lokal offline."
                                        testSuccess = false
                                    }
                                },
                                modifier = Modifier.weight(1.0f),
                                colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Simpan Konfig", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            
                            Button(
                                onClick = {
                                    if (viewModel.supabaseClient.isConfigured) {
                                        viewModel.syncWithSupabase { success, err ->
                                            if (success) {
                                                testResultMsg = "Berhasil sinkronisasi database server & lokal."
                                                testSuccess = true
                                            } else {
                                                testResultMsg = "Gagal Sinkronisasi: $err"
                                                testSuccess = false
                                            }
                                        }
                                    } else {
                                        testResultMsg = "Silakan set & simpan konfig Supabase terlebih dahulu."
                                        testSuccess = false
                                    }
                                },
                                enabled = !isSyncing,
                                modifier = Modifier.weight(1.0f),
                                colors = ButtonDefaults.buttonColors(containerColor = ZuuBorderPurple, contentColor = Color.White),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                if (isSyncing) {
                                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(16.dp))
                                } else {
                                    Text("Sinkronkan Hub", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        testResultMsg?.let { msg ->
                            Surface(
                                color = if (testSuccess) Color(0xFF064E3B) else Color(0xFF7F1D1D),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = msg,
                                    fontSize = 11.sp,
                                    color = if (testSuccess) Color(0xFF34D399) else Color(0xFFFCA5A5),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Button(
                            onClick = { showSqlSchema = !showSqlSchema },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text(if (showSqlSchema) "Sembunyikan SQL Database Script" else "Tampilkan SQL Database Script", color = Color.White, fontSize = 12.sp)
                        }

                        if (showSqlSchema) {
                            val context = LocalContext.current
                            val sqlSchema = """
-- SQL SCRIPT UNTUK SUPABASE SQUADBARBAR365
-- Salin dan jalankan script ini di SQL Editor Supabase Anda untuk membuat tabel otomatis.

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    "fullName" TEXT NOT NULL,
    "whatsappNumber" TEXT NOT NULL UNIQUE,
    "passwordHash" TEXT NOT NULL,
    role TEXT NOT NULL DEFAULT 'Employee',
    "employeeId" TEXT NOT NULL UNIQUE,
    status TEXT NOT NULL DEFAULT 'Pending',
    "warningLevel" INTEGER DEFAULT 0,
    "registeredAt" BIGINT,
    "telegramUsername" TEXT DEFAULT '',
    "bankName" TEXT DEFAULT '',
    "bankAccountNumber" TEXT DEFAULT '',
    "bankAccountName" TEXT DEFAULT '',
    "profilePictureUrl" TEXT DEFAULT '',
    "ms001Username" TEXT DEFAULT '',
    "ms001ReferralLink" TEXT DEFAULT ''
);

CREATE TABLE IF NOT EXISTS tasks (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    "contentCategory" TEXT NOT NULL DEFAULT 'Instagram',
    "videoUrl" TEXT DEFAULT '',
    "imageUrl" TEXT DEFAULT '',
    "fileUrl" TEXT DEFAULT '',
    caption TEXT NOT NULL,
    hashtags TEXT NOT NULL,
    instructions TEXT NOT NULL,
    deadline BIGINT,
    "isHidden" BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS task_submissions (
    id BIGSERIAL PRIMARY KEY,
    "taskId" BIGINT,
    "taskTitle" TEXT NOT NULL,
    "employeeId" TEXT NOT NULL,
    "employeeName" TEXT NOT NULL,
    platform TEXT NOT NULL,
    "postLink" TEXT NOT NULL,
    "screenshotPath" TEXT NOT NULL,
    "submissionDate" BIGINT,
    status TEXT NOT NULL DEFAULT 'Pending Review',
    "rejectionReason" TEXT DEFAULT ''
);

CREATE TABLE IF NOT EXISTS daily_checkins (
    id BIGSERIAL PRIMARY KEY,
    "employeeId" TEXT NOT NULL,
    "dateString" TEXT NOT NULL,
    "checkinTime" BIGINT
);

CREATE TABLE IF NOT EXISTS warnings (
    id BIGSERIAL PRIMARY KEY,
    "employeeId" TEXT NOT NULL,
    "employeeName" TEXT NOT NULL,
    level INTEGER,
    reason TEXT NOT NULL,
    "dateString" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS weekly_ndp (
    id BIGSERIAL PRIMARY KEY,
    "employeeId" TEXT NOT NULL,
    "weekNumber" INTEGER,
    "ndpScore" INTEGER
);

CREATE TABLE IF NOT EXISTS salary_records (
    id BIGSERIAL PRIMARY KEY,
    "employeeId" TEXT NOT NULL,
    "employeeName" TEXT NOT NULL,
    "weekNumber" INTEGER,
    amount DOUBLE PRECISION,
    status TEXT NOT NULL,
    "reasonOfRejection" TEXT DEFAULT '',
    "processedAt" BIGINT
);

CREATE TABLE IF NOT EXISTS bonus_claims (
    id BIGSERIAL PRIMARY KEY,
    "employeeId" TEXT NOT NULL,
    "employeeName" TEXT NOT NULL,
    "platformLink" TEXT NOT NULL,
    "screenshotInsights" TEXT NOT NULL,
    "viewCount" BIGINT,
    "recommendedReward" DOUBLE PRECISION,
    "approvedReward" DOUBLE PRECISION,
    status TEXT NOT NULL,
    reason TEXT DEFAULT '',
    "dateString" TEXT DEFAULT '',
    "claimType" TEXT DEFAULT 'fyp'
);

CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    "employeeId" TEXT NOT NULL,
    "employeeName" TEXT NOT NULL,
    amount DOUBLE PRECISION,
    "paymentType" TEXT NOT NULL,
    status TEXT NOT NULL,
    "dateString" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS announcements (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    "isPinned" BOOLEAN DEFAULT false,
    "createdAt" BIGINT
);

CREATE TABLE IF NOT EXISTS support_tickets (
    id BIGSERIAL PRIMARY KEY,
    "employeeId" TEXT NOT NULL,
    "employeeName" TEXT NOT NULL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    reply TEXT DEFAULT '',
    status TEXT NOT NULL DEFAULT 'Open',
    "createdAt" BIGINT
);

-- SEED DATA (OWNER, ADMIN, SERVICE ENGINEER)
INSERT INTO users ("fullName", "whatsappNumber", "passwordHash", "role", "employeeId", "status", "warningLevel", "registeredAt") 
VALUES 
('Doyok', '08123456789', 'doyok', 'Owner', 'OWNER-001', 'Active', 0, 1718820000000)
ON CONFLICT ("whatsappNumber") DO NOTHING;

INSERT INTO users ("fullName", "whatsappNumber", "passwordHash", "role", "employeeId", "status", "warningLevel", "registeredAt") 
VALUES 
('Dill', '08111111111', 'dill', 'Admin', 'ADM-001', 'Active', 0, 1718820000000),
('Aura', '08111111112', 'aura', 'Admin', 'ADM-002', 'Active', 0, 1718820000000),
('David', '08111111113', 'david', 'Admin', 'ADM-003', 'Active', 0, 1718820000000),
('Hiya', '08111111114', 'hiya', 'Admin', 'ADM-004', 'Active', 0, 1718820000000)
ON CONFLICT ("whatsappNumber") DO NOTHING;

INSERT INTO users ("fullName", "whatsappNumber", "passwordHash", "role", "employeeId", "status", "warningLevel", "registeredAt") 
VALUES 
('Zena', '08555555555', 'zena', 'Service Engineer', 'ENG-001', 'Active', 0, 1718820000000)
ON CONFLICT ("whatsappNumber") DO NOTHING;
""".trimIndent()

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF1E293B))
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("PostgreSQL Schema", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Button(
                                            onClick = {
                                                val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                                val clip = android.content.ClipData.newPlainText("SQL Schema", sqlSchema)
                                                clipboard.setPrimaryClip(clip)
                                                Toast.makeText(context, "SQL Script disalin!", Toast.LENGTH_SHORT).show()
                                            },
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                                            modifier = Modifier.height(28.dp),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text("Salin Script", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Text(
                                        text = sqlSchema,
                                        color = Color.LightGray,
                                        fontSize = 10.sp,
                                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 200.dp)
                                            .verticalScroll(androidx.compose.foundation.rememberScrollState())
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Users list
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Kelola Profil & Hak Otoritas Pengguna 👥", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("${filteredUsers.size} User", color = ZuuAccentGold, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Cari User (Nama, ID, Nomor WA, Role)") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ZuuAccentGold,
                        unfocusedBorderColor = ZuuBorderPurple
                    )
                )
            }

            if (filteredUsers.isEmpty()) {
                item {
                    Text("Tidak ada pengguna sistem ditemukan.", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(vertical = 12.dp))
                }
            } else {
                items(filteredUsers.size) { index ->
                    val user = filteredUsers[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedUserForEdit = user },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                        border = BorderStroke(1.dp, ZuuBorderPurple.copy(alpha = 0.5f))
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = when (user.role) {
                                    "Owner" -> Icons.Default.Star
                                    "Admin" -> Icons.Default.Person
                                    "Service Engineer" -> Icons.Default.Build
                                    else -> Icons.Default.AccountBox
                                },
                                contentDescription = null,
                                tint = when (user.role) {
                                    "Owner" -> ZuuAccentGold
                                    "Admin" -> Color(0xFF10B981)
                                    "Service Engineer" -> Color(0xFF8B5CF6)
                                    else -> Color.White
                                },
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(user.fullName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("ID: ${user.employeeId} • WA: ${user.whatsappNumber}", color = ZuuSoftLavender, fontSize = 11.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Role: ${user.role}", 
                                        color = when (user.role) {
                                            "Owner" -> ZuuAccentGold
                                            "Admin" -> Color(0xFF3B82F6)
                                            "Service Engineer" -> Color(0xFF8B5CF6)
                                            else -> ZuuSoftLavender
                                        }, 
                                        fontSize = 11.sp, 
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("•", color = Color.Gray, fontSize = 11.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = user.status, 
                                        color = when (user.status) {
                                            "Active" -> Color(0xFF10B981)
                                            "Pending" -> Color(0xFFF59E0B)
                                            "Suspended" -> Color(0xFFEC4899)
                                            else -> Color.Red
                                        }, 
                                        fontSize = 11.sp, 
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                            Icon(Icons.Default.Edit, "Edit Otoritas", tint = Color.LightGray, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            // System Logs Activity View
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Text("Audit Aktivitas Platform 📜", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ZuuDarkAppBar)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        val displayLogs = logs.take(15)
                        if (displayLogs.isEmpty()) {
                            Text("Belum ada aktivitas yang direkam sistem.", color = Color.Gray, fontSize = 12.sp)
                        } else {
                            displayLogs.forEachIndexed { i, log ->
                                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("[${log.action}]", color = ZuuAccentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Text(log.timestamp.toString(), color = Color.Gray, fontSize = 9.sp)
                                    }
                                    Text("Oleh: ${log.employeeId} - ${log.details}", color = Color.White, fontSize = 11.sp)
                                    if (i < displayLogs.size - 1) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Divider(color = Color.White.copy(alpha = 0.05f))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Modal dialogue of chosen user edit permissions:
    selectedUserForEdit?.let { user ->
        var selectedRole by remember { mutableStateOf(user.role) }
        var selectedStatus by remember { mutableStateOf(user.status) }
        var selectedSP by remember { mutableStateOf(user.warningLevel) }

        val rolesList = listOf("Owner", "Admin", "Employee", "Service Engineer")
        val statusList = listOf("Active", "Pending", "Rejected", "Suspended")

        AlertDialog(
            onDismissRequest = { selectedUserForEdit = null },
            title = { Text("Edit Hak Akses Pengguna", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            containerColor = ZuuCardPurple,
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("User: ${user.fullName} (${user.employeeId})", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    
                    Text("Konfigurasi Tingkat Otoritas (Role):", color = ZuuSoftLavender, fontSize = 12.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rolesList.forEach { role ->
                            Card(
                                modifier = Modifier.clickable { selectedRole = role },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedRole == role) ZuuAccentGold else ZuuDeepPurpleBg
                                ),
                                border = BorderStroke(1.dp, ZuuBorderPurple)
                            ) {
                                Text(
                                    text = role,
                                    fontSize = 11.sp,
                                    color = if (selectedRole == role) ZuuDeepPurpleBg else Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Text("Status Keanggotaan:", color = ZuuSoftLavender, fontSize = 12.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        statusList.forEach { stat ->
                            Card(
                                modifier = Modifier.clickable { selectedStatus = stat },
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedStatus == stat) ZuuAccentGold else ZuuDeepPurpleBg
                                ),
                                border = BorderStroke(1.dp, ZuuBorderPurple)
                            ) {
                                Text(
                                    text = stat,
                                    fontSize = 11.sp,
                                    color = if (selectedStatus == stat) ZuuDeepPurpleBg else Color.White,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Text("Tingkat Sanksi SP (Warning):", color = ZuuSoftLavender, fontSize = 12.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf(0, 1, 2, 3).forEach { spLevel ->
                            Button(
                                onClick = { selectedSP = spLevel },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedSP == spLevel) Color(0xFFEF4444) else ZuuBorderPurple,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("SP-$spLevel", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    if (user.role == "Employee") {
                        HorizontalDivider(color = ZuuBorderPurple, modifier = Modifier.padding(vertical = 4.dp))
                        Text("Registrasi Eksternal BARBAR365:", color = ZuuAccentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("• ID / Username: ${user.ms001Username.ifBlank { "Belum Terdaftar" }}", color = Color.LightGray, fontSize = 11.sp)
                        Text("• Referral: ${user.ms001ReferralLink.ifBlank { "Belum Ada Link" }}", color = Color.LightGray, fontSize = 11.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.serviceEngineerUpdateUser(user, selectedRole, selectedStatus, selectedSP) {
                            selectedUserForEdit = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg)
                ) {
                    Text("Simpan", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedUserForEdit = null }) {
                    Text("Batal", color = Color.White)
                }
            }
        )
    }
}

@Composable
fun Ms001RegistrationScreen(viewModel: ZuuViewModel) {
    val context = LocalContext.current
    val currentUser by viewModel.currentUser.collectAsState()
    
    var username by remember { mutableStateOf("") }
    var referralLink by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = ZuuAccentGold,
        unfocusedBorderColor = ZuuBorderPurple,
        focusedLabelColor = ZuuAccentGold,
        unfocusedLabelColor = ZuuSoftLavender,
        cursorColor = ZuuAccentGold,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ZuuDeepPurpleBg)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ZuuLogo(modifier = Modifier.size(90.dp))
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Onboarding Karyawan Baru",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Selamat! Akun Anda telah disetujui. Langkah terakhir sebelum masuk ke dashboard utama adalah registrasi eksternal.",
                fontSize = 12.sp,
                color = ZuuSoftLavender,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                border = BorderStroke(1.dp, ZuuBorderPurple)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "1. DAFTAR INDUK DI WEBSITE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ZuuAccentGold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Silakan klik tombol di bawah untuk mendaftar di website resmi mitra kami terlebih dahulu.",
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://ms001.barbar365a.site"))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Web Link", tint = ZuuDeepPurpleBg, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "BUKA WEBSITE BARBAR365",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                border = BorderStroke(1.dp, ZuuBorderPurple)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "2. MASUKKAN DATA REGISTRASI WEB",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ZuuAccentGold,
                        letterSpacing = 1.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("ID / Username BARBAR365") },
                        placeholder = { Text("Username yang didaftarkan di web BARBAR365", color = Color.Gray) },
                        textStyle = TextStyle(color = Color.White),
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors,
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Person, null, tint = ZuuSoftLavender) }
                    )

                    OutlinedTextField(
                        value = referralLink,
                        onValueChange = { referralLink = it },
                        label = { Text("Link Referral BARBAR365") },
                        placeholder = { Text("https://ms001.barbar365a.site/ref/...", color = Color.Gray) },
                        textStyle = TextStyle(color = Color.White),
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors,
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Send, null, tint = ZuuSoftLavender) }
                    )
                }
            }

            if (errorText != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorText ?: "",
                    color = Color(0xFFEF4444),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (username.isBlank()) {
                        errorText = "Username BARBAR365 tidak boleh kosong."
                    } else if (referralLink.isBlank()) {
                        errorText = "Link Referral tidak boleh kosong."
                    } else if (!referralLink.trim().lowercase().startsWith("http")) {
                        errorText = "Link Referral harus berupa URL valid dimulai dengan http:// atau https://"
                    } else {
                        errorText = null
                        isLoading = true
                        viewModel.registerMs001Account(username, referralLink) { success, err ->
                            isLoading = false
                            if (success) {
                                Toast.makeText(context, "Registrasi Onboarding Selesai!", Toast.LENGTH_LONG).show()
                            } else {
                                errorText = err ?: "Terjadi kesalahan."
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ZuuAccentGold, contentColor = ZuuDeepPurpleBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = ZuuDeepPurpleBg)
                } else {
                    Text("SIMPAN & LANJUTKAN KE DASHBOARD", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Keluar dari Sesi",
                color = Color.LightGray,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { viewModel.logout() }
                    .padding(8.dp)
            )
        }
    }
}

// --- ADMIN CONTENT SCHEDULING CALENDAR SCREEN ---
@Composable
fun AdminContentCalendarScreen(viewModel: ZuuViewModel) {
    val tasksList by viewModel.tasks.collectAsState()
    val context = LocalContext.current
    
    // Calendar view state
    var displayedCalendar by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    
    // Drag & Drop State
    var draggedTask by remember { mutableStateOf<Task?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var hoveredDate by remember { mutableStateOf<Long?>(null) }
    
    // Target boundaries map (for matching pointer coords to days)
    val cellBoundsMap = remember { mutableStateMapOf<Long, LayoutCoordinates>() }
    // Source item start positions (for calculating offset from where drag began)
    val itemCoordinatesMap = remember { mutableStateMapOf<Long, LayoutCoordinates>() }
    
    // Generate dates helper for the month grid
    val daysInMonth = remember(displayedCalendar) {
        val tempCal = displayedCalendar.clone() as Calendar
        tempCal.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK)
        val maxDays = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        
        val list = mutableListOf<Calendar>()
        
        // Backfill previous month days
        val prevMonthCal = tempCal.clone() as Calendar
        prevMonthCal.add(Calendar.MONTH, -1)
        val prevMaxDays = prevMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val backfillCount = firstDayOfWeek - 1
        for (i in (prevMaxDays - backfillCount + 1)..prevMaxDays) {
            val entry = prevMonthCal.clone() as Calendar
            entry.set(Calendar.DAY_OF_MONTH, i)
            list.add(entry)
        }
        
        // Current month days
        for (i in 1..maxDays) {
            val entry = tempCal.clone() as Calendar
            entry.set(Calendar.DAY_OF_MONTH, i)
            list.add(entry)
        }
        
        // Outer forward fill next month
        val totalCellsNeeded = if (list.size <= 35) 35 else 42
        val nextMonthCal = tempCal.clone() as Calendar
        nextMonthCal.add(Calendar.MONTH, 1)
        val forwardfillCount = totalCellsNeeded - list.size
        for (i in 1..forwardfillCount) {
            val entry = nextMonthCal.clone() as Calendar
            entry.set(Calendar.DAY_OF_MONTH, i)
            list.add(entry)
        }
        list
    }

    // Helper functions
    fun getStartOfDayTime(cal: Calendar): Long {
        val temp = cal.clone() as Calendar
        temp.set(Calendar.HOUR_OF_DAY, 0)
        temp.set(Calendar.MINUTE, 0)
        temp.set(Calendar.SECOND, 0)
        temp.set(Calendar.MILLISECOND, 0)
        return temp.timeInMillis
    }
    
    val monthNames = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )
    
    fun getMonthHeader(cal: Calendar): String {
        return "${monthNames[cal.get(Calendar.MONTH)]} ${cal.get(Calendar.YEAR)}"
    }

    fun formatDateId(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
        return sdf.format(Date(timestamp))
    }

    // Drag Lookup function pointer bound matching
    fun findHoveredDate(point: Offset): Long? {
        for ((date, coords) in cellBoundsMap) {
            if (coords.isAttached) {
                val rect = coords.boundsInRoot()
                if (rect.contains(point)) {
                    return date
                }
            }
        }
        return null
    }

    // Scaffold visual wrapper
    AdminDrawerScaffold(viewModel = viewModel, title = "Kalender Konten") {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Month Header line
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val prev = displayedCalendar.clone() as Calendar
                            prev.add(Calendar.MONTH, -1)
                            displayedCalendar = prev
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Bulan Sebelumnya", tint = Color.White)
                    }
                    
                    Text(
                        text = getMonthHeader(displayedCalendar),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ZuuAccentGold
                    )
                    
                    IconButton(
                        onClick = {
                            val next = displayedCalendar.clone() as Calendar
                            next.add(Calendar.MONTH, 1)
                            displayedCalendar = next
                        }
                    ) {
                        Icon(Icons.Default.ArrowForward, "Bulan Berikutnya", tint = Color.White)
                    }
                }
                
                Spacer(modifier = Modifier.height(14.dp))
                
                // Days Headers titles
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    val weekdays = listOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
                    weekdays.forEach { dayName ->
                        Text(
                            text = dayName,
                            color = ZuuSoftLavender,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Grid Content
                Column(
                    modifier = Modifier.weight(1.3f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val rowsCount = daysInMonth.size / 7
                    for (row in 0 until rowsCount) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            for (col in 0..6) {
                                val dayCal = daysInMonth[row * 7 + col]
                                val cellDate = getStartOfDayTime(dayCal)
                                val isCurrentMonth = dayCal.get(Calendar.MONTH) == displayedCalendar.get(Calendar.MONTH)
                                val isToday = getStartOfDayTime(Calendar.getInstance()) == cellDate
                                val isSelected = selectedDate == cellDate
                                val isHovered = hoveredDate == cellDate
                                
                                val dayTasks = tasksList.filter {
                                    getStartOfDayTime(Calendar.getInstance().apply { timeInMillis = it.deadline }) == cellDate
                                }
                                
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1.1f)
                                        .onGloballyPositioned { coords ->
                                            cellBoundsMap[cellDate] = coords
                                        }
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isHovered) ZuuAccentGold.copy(alpha = 0.35f)
                                            else if (isToday) ZuuBorderPurple.copy(alpha = 0.9f)
                                            else if (isCurrentMonth) ZuuCardPurple
                                            else ZuuCardPurple.copy(alpha = 0.3f)
                                        )
                                        .border(
                                            width = if (isHovered) 2.dp else if (isSelected) 1.5.dp else 1.dp,
                                            color = if (isHovered) ZuuAccentGold 
                                                    else if (isSelected) ZuuAccentGold.copy(alpha = 0.8f) 
                                                    else if (isToday) ZuuAccentGold.copy(alpha = 0.5f)
                                                    else ZuuBorderPurple,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            selectedDate = cellDate
                                        },
                                    contentAlignment = Alignment.TopStart
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = dayCal.get(Calendar.DAY_OF_MONTH).toString(),
                                            color = if (isCurrentMonth) Color.White else Color.White.copy(alpha = 0.4f),
                                            fontWeight = if (isToday) FontWeight.ExtraBold else FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                        
                                        if (dayTasks.isNotEmpty()) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                dayTasks.take(3).forEach { task ->
                                                    val dotColor = when (task.contentCategory) {
                                                        "Instagram" -> ZuuAccentGold
                                                        "TikTok" -> Color(0xFFE2E8F0)
                                                        "Facebook" -> Color(0xFF3B82F6)
                                                        "YouTube Shorts" -> Color(0xFFEF4444)
                                                        else -> ZuuSoftLavender
                                                    }
                                                    Box(
                                                        modifier = Modifier
                                                            .size(6.dp)
                                                            .clip(CircleShape)
                                                            .background(dotColor)
                                                    )
                                                }
                                                if (dayTasks.size > 3) {
                                                    Text(
                                                        text = "+${dayTasks.size - 3}",
                                                        color = ZuuSoftLavender,
                                                        fontSize = 7.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = if (selectedDate != null) "Tugas Terjadwal (${formatDateId(selectedDate!!)}) 📅"
                           else "Pilih tanggal untuk melihat detail jadwal",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(ZuuCardPurple)
                        .border(1.dp, ZuuBorderPurple, RoundedCornerShape(12.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filteredTasks = tasksList.filter {
                        if (selectedDate == null) false
                        else getStartOfDayTime(Calendar.getInstance().apply { timeInMillis = it.deadline }) == selectedDate
                    }
                    
                    if (filteredTasks.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = null,
                                        tint = ZuuSoftLavender.copy(alpha = 0.5f),
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Tidak ada tugas dijadwalkan.",
                                        fontSize = 11.sp,
                                        color = ZuuSoftLavender
                                    )
                                    Text(
                                        text = "Seret draf tugas dari loker di bawah ke tanggal di atas untuk menjadwal ulang!",
                                        fontSize = 9.sp,
                                        color = ZuuSoftLavender.copy(alpha = 0.7f),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        items(filteredTasks) { task ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coords ->
                                        itemCoordinatesMap[task.id] = coords
                                    }
                                    .pointerInput(task) {
                                        detectDragGesturesAfterLongPress(
                                            onDragStart = { offset ->
                                                draggedTask = task
                                                val rootPos = itemCoordinatesMap[task.id]?.positionInRoot() ?: Offset.Zero
                                                dragOffset = rootPos + offset
                                            },
                                            onDrag = { change, dragAmount ->
                                                change.consume()
                                                dragOffset += dragAmount
                                                hoveredDate = findHoveredDate(dragOffset)
                                            },
                                            onDragEnd = {
                                                if (hoveredDate != null && draggedTask != null) {
                                                    viewModel.updateTask(draggedTask!!.copy(deadline = hoveredDate!!))
                                                    Toast.makeText(context, "Konten '${draggedTask!!.title}' berhasil dijadwal ulang ke ${formatDateId(hoveredDate!!)}!", Toast.LENGTH_SHORT).show()
                                                }
                                                draggedTask = null
                                                hoveredDate = null
                                            },
                                            onDragCancel = {
                                                draggedTask = null
                                                hoveredDate = null
                                            }
                                        )
                                    },
                                colors = CardDefaults.cardColors(containerColor = ZuuDeepPurpleBg),
                                border = BorderStroke(1.dp, ZuuBorderPurple)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(task.title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 12.sp)
                                        Text(task.description, color = ZuuSoftLavender, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                    TagCategory(task.contentCategory)
                                }
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(10.dp))
                
                var showDraftDrawer by remember { mutableStateOf(false) }
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ZuuCardPurple),
                    border = BorderStroke(1.dp, ZuuBorderPurple)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDraftDrawer = !showDraftDrawer },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Build, null, tint = ZuuAccentGold, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Loker Draf / Antrean Tugas 🛠️", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 12.sp)
                            }
                            Icon(
                                imageVector = if (showDraftDrawer) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        
                        if (showDraftDrawer) {
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            val unscheduledTasks = tasksList.filter { it.deadline == 0L || getStartOfDayTime(Calendar.getInstance().apply { timeInMillis = it.deadline }) < getStartOfDayTime(Calendar.getInstance()) }
                            
                            if (unscheduledTasks.isEmpty()) {
                                Text("Loker kosong. Semua tugas telah dijadwalkan dengan aktif!", color = ZuuSoftLavender, fontSize = 10.sp, modifier = Modifier.padding(vertical = 10.dp))
                            } else {
                                Box(modifier = Modifier.height(130.dp)) {
                                    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        items(unscheduledTasks) { draf ->
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .onGloballyPositioned { coords ->
                                                        itemCoordinatesMap[draf.id] = coords
                                                    }
                                                    .pointerInput(draf) {
                                                        detectDragGesturesAfterLongPress(
                                                            onDragStart = { offset ->
                                                                draggedTask = draf
                                                                val rootPos = itemCoordinatesMap[draf.id]?.positionInRoot() ?: Offset.Zero
                                                                dragOffset = rootPos + offset
                                                            },
                                                            onDrag = { change, dragAmount ->
                                                                change.consume()
                                                                dragOffset += dragAmount
                                                                hoveredDate = findHoveredDate(dragOffset)
                                                            },
                                                            onDragEnd = {
                                                                if (hoveredDate != null && draggedTask != null) {
                                                                    viewModel.updateTask(draggedTask!!.copy(deadline = hoveredDate!!))
                                                                    Toast.makeText(context, "Draf Konten '${draggedTask!!.title}' berhasil dijadwalkan ke ${formatDateId(hoveredDate!!)}!", Toast.LENGTH_SHORT).show()
                                                                }
                                                                draggedTask = null
                                                                hoveredDate = null
                                                            },
                                                            onDragCancel = {
                                                                draggedTask = null
                                                                hoveredDate = null
                                                            }
                                                        )
                                                    },
                                                colors = CardDefaults.cardColors(containerColor = ZuuDeepPurpleBg),
                                                border = BorderStroke(1.dp, ZuuBorderPurple)
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Text(draf.title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 11.sp)
                                                        Text("Target: ${draf.contentCategory}", color = ZuuSoftLavender, fontSize = 9.sp)
                                                    }
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text("Tahan & Seret 🖐️", color = ZuuAccentGold, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        TagCategory(draf.contentCategory)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Drag Drop Overlay
            if (draggedTask != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.25f))
                ) {
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                translationX = dragOffset.x - 100.dp.toPx()
                                translationY = dragOffset.y - 30.dp.toPx()
                                scaleX = 1.08f
                                scaleY = 1.08f
                                alpha = 0.9f
                                rotationZ = 3f
                            }
                            .width(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(ZuuCardPurple)
                            .border(2.dp, ZuuAccentGold, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                draggedTask!!.title,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                draggedTask!!.contentCategory,
                                color = ZuuAccentGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TagCategory(category: String) {
    val (bgColor, textColor) = when (category) {
        "Instagram" -> Pair(ZuuAccentGold.copy(alpha = 0.2f), ZuuAccentGold)
        "TikTok" -> Pair(Color(0xFFE2E8F0).copy(alpha = 0.2f), Color(0xFFE2E8F0))
        "Facebook" -> Pair(Color(0xFF3B82F6).copy(alpha = 0.2f), Color(0xFF3B82F6))
        "YouTube Shorts" -> Pair(Color(0xFFEF4444).copy(alpha = 0.2f), Color(0xFFEF4444))
        else -> Pair(ZuuSoftLavender.copy(alpha = 0.2f), ZuuSoftLavender)
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(category, color = textColor, fontSize = 9.sp, fontWeight = FontWeight.Bold)
    }
}

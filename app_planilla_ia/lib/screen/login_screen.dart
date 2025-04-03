import 'package:flutter/material.dart';

import 'home_screen/home_screen_planilla.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<double> _opacityAnimation;
  late Animation<double> _scaleAnimation;
  late Animation<Offset> _slideAnimation;

  final FocusNode _emailFocus = FocusNode();
  final FocusNode _passwordFocus = FocusNode();

  bool _isPasswordVisible = false;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 800),
    );

    _opacityAnimation = Tween<double>(begin: 0.0, end: 1.0).animate(
      CurvedAnimation(
        parent: _controller,
        curve: const Interval(0.0, 0.5, curve: Curves.easeIn),
      ),
    );

    _scaleAnimation = Tween<double>(begin: 0.95, end: 1.0).animate(
      CurvedAnimation(
        parent: _controller,
        curve: Curves.easeOutBack,
      ),
    );

    _slideAnimation = Tween<Offset>(
      begin: const Offset(0, 0.2),
      end: Offset.zero,
    ).animate(CurvedAnimation(
      parent: _controller,
      curve: Curves.easeOut,
    ));

    _controller.forward();
  }

  @override
  void dispose() {
    _controller.dispose();
    _emailFocus.dispose();
    _passwordFocus.dispose();
    super.dispose();
  }

  Future<void> _simulateLogin() async {
    setState(() => _isLoading = true);
    await Future.delayed(const Duration(seconds: 2));
    setState(() => _isLoading = false);
    // Aquí iría la lógica real de autenticación
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Iniciar Sesión"),
        elevation: 0,
        backgroundColor: Colors.transparent,
        foregroundColor: Colors.blueAccent,
      ),
      body: FadeTransition(
        opacity: _opacityAnimation,
        child: ScaleTransition(
          scale: _scaleAnimation,
          child: SlideTransition(
            position: _slideAnimation,
            child: Container(
              decoration: BoxDecoration(
                gradient: LinearGradient(
                  begin: Alignment.topCenter,
                  end: Alignment.bottomCenter,
                  colors: [
                    Colors.white,
                    Colors.blue.shade50,
                  ],
                ),
              ),
              child: Center(
                child: SingleChildScrollView(
                  padding: const EdgeInsets.all(32.0),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      // Logo con sombra
                      Container(
                        decoration: BoxDecoration(
                          color: Colors.white,
                          shape: BoxShape.circle,
                          boxShadow: [
                            BoxShadow(
                              color: Colors.blue.withOpacity(0.2),
                              blurRadius: 15,
                              spreadRadius: 5,
                            ),
                          ],
                        ),
                        child: const Padding(
                          padding: EdgeInsets.all(16.0),
                          child: Icon(
                            Icons.account_balance_wallet,
                            size: 60,
                            color: Colors.blueAccent,
                          ),
                        ),
                      ),
                      const SizedBox(height: 30),

                      // Card con los campos de login
                      Card(
                        elevation: 8,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(20),
                        ),
                        child: Padding(
                          padding: const EdgeInsets.all(24.0),
                          child: Column(
                            children: [
                              // Campo de email con animación de enfoque
                              AnimatedContainer(
                                duration: const Duration(milliseconds: 200),
                                decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(12),
                                  border: Border.all(
                                    color: _emailFocus.hasFocus
                                        ? Colors.blueAccent
                                        : Colors.grey.shade300,
                                    width: _emailFocus.hasFocus ? 2 : 1,
                                  ),
                                  boxShadow: _emailFocus.hasFocus
                                      ? [
                                          BoxShadow(
                                            color: Colors.blueAccent
                                                .withOpacity(0.1),
                                            blurRadius: 10,
                                            spreadRadius: 2,
                                          ),
                                        ]
                                      : null,
                                ),
                                child: Padding(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 16),
                                  child: TextField(
                                    focusNode: _emailFocus,
                                    decoration: const InputDecoration(
                                      labelText: "Usuario o Email",
                                      border: InputBorder.none,
                                      icon: Icon(Icons.person_outline),
                                    ),
                                    style: const TextStyle(fontSize: 16),
                                    keyboardType: TextInputType.emailAddress,
                                  ),
                                ),
                              ),
                              const SizedBox(height: 20),

                              // Campo de contraseña con toggle de visibilidad
                              AnimatedContainer(
                                duration: const Duration(milliseconds: 200),
                                decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(12),
                                  border: Border.all(
                                    color: _passwordFocus.hasFocus
                                        ? Colors.blueAccent
                                        : Colors.grey.shade300,
                                    width: _passwordFocus.hasFocus ? 2 : 1,
                                  ),
                                  boxShadow: _passwordFocus.hasFocus
                                      ? [
                                          BoxShadow(
                                            color: Colors.blueAccent
                                                .withOpacity(0.1),
                                            blurRadius: 10,
                                            spreadRadius: 2,
                                          ),
                                        ]
                                      : null,
                                ),
                                child: Padding(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 16),
                                  child: TextField(
                                    focusNode: _passwordFocus,
                                    obscureText: !_isPasswordVisible,
                                    decoration: InputDecoration(
                                      labelText: "Contraseña",
                                      border: InputBorder.none,
                                      icon: const Icon(Icons.lock_outline),
                                      suffixIcon: IconButton(
                                        icon: Icon(
                                          _isPasswordVisible
                                              ? Icons.visibility
                                              : Icons.visibility_off,
                                          color: Colors.grey,
                                        ),
                                        onPressed: () {
                                          setState(() {
                                            _isPasswordVisible =
                                                !_isPasswordVisible;
                                          });
                                        },
                                      ),
                                    ),
                                    style: const TextStyle(fontSize: 16),
                                  ),
                                ),
                              ),
                              const SizedBox(height: 30),

                              // Botón de login con efecto de carga
                              SizedBox(
                                width: double.infinity,
                                child: ElevatedButton(
                                  onPressed: _isLoading
                                      ? null
                                      : () async {
                                          await _simulateLogin();
                                          if (!_isLoading) {
                                            Navigator.push(
                                              // ignore: use_build_context_synchronously
                                              context,
                                              MaterialPageRoute(
                                                builder: (context) =>
                                                    const HomeScreenPlanilla(),
                                              ),
                                            );
                                          }
                                        },
                                  style: ElevatedButton.styleFrom(
                                    backgroundColor: Colors.blueAccent,
                                    foregroundColor: Colors.white,
                                    padding: const EdgeInsets.symmetric(
                                        vertical: 16),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(12),
                                    ),
                                    elevation: 5,
                                    shadowColor:
                                        Colors.blueAccent.withOpacity(0.3),
                                  ),
                                  child: _isLoading
                                      ? const SizedBox(
                                          width: 20,
                                          height: 20,
                                          child: CircularProgressIndicator(
                                            strokeWidth: 2,
                                            valueColor:
                                                AlwaysStoppedAnimation<Color>(
                                                    Colors.white),
                                          ),
                                        )
                                      : const Text(
                                          "Ingresar",
                                          style: TextStyle(
                                            fontSize: 18,
                                            fontWeight: FontWeight.bold,
                                          ),
                                        ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(height: 20),

                      // Enlace de recuperación de contraseña
                      TextButton(
                        onPressed: () {
                          // Lógica para recuperar contraseña
                        },
                        style: TextButton.styleFrom(
                          foregroundColor: Colors.blueAccent,
                        ),
                        child: const Text("¿Olvidaste tu contraseña?"),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

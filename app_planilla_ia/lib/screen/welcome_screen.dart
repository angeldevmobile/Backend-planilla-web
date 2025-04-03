import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart' show timeDilation;

import 'login_screen.dart';

class WelcomeScreen extends StatefulWidget {
  const WelcomeScreen({super.key});

  @override
  State<WelcomeScreen> createState() => _WelcomeScreenState();
}

class _WelcomeScreenState extends State<WelcomeScreen>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<double> _animation;
  late Animation<double> _opacityAnimation;
  late Animation<Offset> _slideAnimation;
  double _hoverValue = 0.0;
  bool _buttonHovered = false;

  @override
  void initState() {
    super.initState();
    timeDilation = 1.5; // Solo para desarrollo

    _controller = AnimationController(
      vsync: this,
      duration: const Duration(seconds: 1),
    );

    _animation = CurvedAnimation(
      parent: _controller,
      curve: Curves.easeInOut,
    );

    _opacityAnimation = Tween<double>(begin: 0.0, end: 1.0).animate(
      CurvedAnimation(
        parent: _controller,
        curve: const Interval(0.0, 0.5, curve: Curves.easeIn),
      ),
    );

    _slideAnimation = Tween<Offset>(
      begin: const Offset(0, 0.5),
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
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    final isMobile = size.width < 600;
    final isTablet = size.width >= 600 && size.width < 1000;
    final isDesktop = size.width >= 1000;

    return Scaffold(
      backgroundColor: Colors.blueAccent,
      body: Stack(
        children: [
          // Fondo animado
          Positioned.fill(
            child: Container(
              decoration: BoxDecoration(
                gradient: LinearGradient(
                  begin: Alignment.topCenter,
                  end: Alignment.bottomCenter,
                  colors: [
                    Colors.blueAccent.shade700,
                    Colors.blueAccent.shade400,
                    Colors.blueAccent.shade200,
                  ],
                ),
              ),
              child: AnimatedBubbles(isDesktop: isDesktop),
            ),
          ),

          // Contenido principal
          Center(
            child: SingleChildScrollView(
              child: FadeTransition(
                opacity: _opacityAnimation,
                child: SlideTransition(
                  position: _slideAnimation,
                  child: Padding(
                    padding: EdgeInsets.all(isMobile ? 20.0 : 40.0),
                    child: MouseRegion(
                      onHover: isDesktop
                          ? (event) {
                              setState(() {
                                final centerX = size.width / 2;
                                _hoverValue =
                                    ((event.position.dx - centerX) / centerX) *
                                        0.05;
                              });
                            }
                          : null,
                      child: Transform(
                        transform: isDesktop
                            ? (Matrix4.identity()
                              ..setEntry(3, 2, 0.001)
                              ..rotateY(_hoverValue))
                            : Matrix4.identity(),
                        alignment: FractionalOffset.center,
                        child: Container(
                          constraints: BoxConstraints(
                            maxWidth: isMobile ? double.infinity : 600,
                          ),
                          padding: EdgeInsets.all(isMobile ? 20.0 : 30.0),
                          decoration: BoxDecoration(
                            color: Colors.white.withOpacity(0.1),
                            borderRadius: BorderRadius.circular(20),
                            boxShadow: [
                              BoxShadow(
                                color: Colors.black.withOpacity(0.2),
                                blurRadius: 30,
                                spreadRadius: 5,
                              ),
                            ],
                          ),
                          child: Column(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              // Icono
                              ScaleTransition(
                                scale: _animation,
                                child: Container(
                                  decoration: BoxDecoration(
                                    color: Colors.white.withOpacity(0.2),
                                    shape: BoxShape.circle,
                                    boxShadow: [
                                      BoxShadow(
                                        color: Colors.white.withOpacity(0.4),
                                        blurRadius: 20,
                                        spreadRadius: 5,
                                      ),
                                    ],
                                  ),
                                  child: Padding(
                                    padding:
                                        EdgeInsets.all(isMobile ? 15.0 : 25.0),
                                    child: Icon(
                                      Icons.account_balance_wallet,
                                      size: isMobile ? 60 : 80,
                                      color: Colors.white,
                                    ),
                                  ),
                                ),
                              ),
                              SizedBox(height: isMobile ? 20 : 30),

                              // Título
                              NeonText(
                                text: "SmartPayroll",
                                fontSize: isMobile ? 32 : 42,
                                color: Colors.white,
                                blurRadius: isMobile ? 10 : 15,
                              ),
                              SizedBox(height: isMobile ? 10 : 15),
                              Text(
                                "Gestión de nómina inteligente",
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontSize: isMobile ? 14 : 18,
                                  color: Colors.white70,
                                  fontStyle: FontStyle.italic,
                                ),
                              ),
                              SizedBox(height: isMobile ? 30 : 40),

                              // Botón
                              MouseRegion(
                                cursor: SystemMouseCursors.click,
                                onEnter: (_) =>
                                    setState(() => _buttonHovered = true),
                                onExit: (_) =>
                                    setState(() => _buttonHovered = false),
                                child: AnimatedContainer(
                                  duration: const Duration(milliseconds: 300),
                                  decoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(30),
                                    boxShadow: _buttonHovered
                                        ? [
                                            BoxShadow(
                                              color:
                                                  Colors.white.withOpacity(0.6),
                                              blurRadius: 20,
                                              spreadRadius: 5,
                                            ),
                                          ]
                                        : [
                                            BoxShadow(
                                              color:
                                                  Colors.white.withOpacity(0.4),
                                              blurRadius: 10,
                                              spreadRadius: 2,
                                            ),
                                          ],
                                  ),
                                  child: ElevatedButton(
                                    onPressed: () {
                                      Navigator.push(
                                        context,
                                        PageRouteBuilder(
                                          transitionDuration:
                                              const Duration(milliseconds: 800),
                                          pageBuilder: (_, __, ___) =>
                                              const LoginScreen(),
                                          transitionsBuilder:
                                              (_, animation, __, child) {
                                            return FadeTransition(
                                              opacity: animation,
                                              child: SlideTransition(
                                                position: Tween<Offset>(
                                                  begin: const Offset(0, 0.3),
                                                  end: Offset.zero,
                                                ).animate(
                                                  CurvedAnimation(
                                                    parent: animation,
                                                    curve: Curves.easeOutQuart,
                                                  ),
                                                ),
                                                child: child,
                                              ),
                                            );
                                          },
                                        ),
                                      );
                                    },
                                    style: ElevatedButton.styleFrom(
                                      backgroundColor: Colors.white,
                                      foregroundColor: Colors.blueAccent,
                                      padding: EdgeInsets.symmetric(
                                        horizontal: isMobile ? 30 : 50,
                                        vertical: isMobile ? 12 : 18,
                                      ),
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(30),
                                      ),
                                      elevation: isMobile ? 5 : 10,
                                    ),
                                    child: Text(
                                      "Iniciar Sesión",
                                      style: TextStyle(
                                        fontSize: isMobile ? 16 : 20,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                  ),
                                ),
                              ),

                              // Solo para desktop/tablet
                              if (!isMobile) ...[
                                const SizedBox(height: 20),
                                Text(
                                  "Presiona Enter para continuar",
                                  style: TextStyle(
                                    fontSize: isTablet ? 12 : 14,
                                    color: Colors.white54,
                                  ),
                                ),
                              ],
                            ],
                          ),
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ),

          // Footer (solo para web/tablet)
          if (!isMobile)
            Positioned(
              bottom: 20,
              left: 0,
              right: 0,
              child: SafeArea(
                child: Column(
                  children: [
                    Text(
                      "Optimizado para Chrome, Edge y Firefox",
                      style: TextStyle(
                        color: Colors.white54,
                        fontSize: isTablet ? 10 : 12,
                      ),
                    ),
                    const SizedBox(height: 5),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        IconButton(
                          icon: Icon(Icons.language,
                              color: Colors.white54, size: isTablet ? 18 : 24),
                          onPressed: () {},
                          tooltip: "Idioma",
                        ),
                        IconButton(
                          icon: Icon(Icons.help_outline,
                              color: Colors.white54, size: isTablet ? 18 : 24),
                          onPressed: () {},
                          tooltip: "Ayuda",
                        ),
                        IconButton(
                          icon: Icon(Icons.phone,
                              color: Colors.white54, size: isTablet ? 18 : 24),
                          onPressed: () {},
                          tooltip: "Contacto",
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
        ],
      ),
    );
  }
}

// Widget para el efecto de texto neón (mejorado para web)
class NeonText extends StatelessWidget {
  final String text;
  final double fontSize;
  final Color color;
  final double blurRadius;

  const NeonText({
    super.key,
    required this.text,
    this.fontSize = 20,
    this.color = Colors.white,
    this.blurRadius = 5,
  });

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Text(
          text,
          style: TextStyle(
            fontSize: fontSize,
            fontWeight: FontWeight.bold,
            color: color,
            shadows: [
              Shadow(
                  color: color,
                  blurRadius: blurRadius,
                  offset: const Offset(0, 0)),
              Shadow(
                  color: color.withOpacity(0.7),
                  blurRadius: blurRadius * 1.5,
                  offset: const Offset(0, 0)),
              Shadow(
                  color: color.withOpacity(0.4),
                  blurRadius: blurRadius * 3,
                  offset: const Offset(0, 0)),
            ],
          ),
        ),
        Text(
          text,
          style: TextStyle(
            fontSize: fontSize,
            fontWeight: FontWeight.bold,
            color: color.withOpacity(0.9),
          ),
        ),
      ],
    );
  }
}

// Widget para las burbujas animadas en el fondo (optimizado para web)
class AnimatedBubbles extends StatefulWidget {
  final bool isDesktop;
  const AnimatedBubbles({super.key, required this.isDesktop});

  @override
  State<AnimatedBubbles> createState() => _AnimatedBubblesState();
}

class _AnimatedBubblesState extends State<AnimatedBubbles>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  final List<Bubble> bubbles = [];

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      vsync: this,
      duration: const Duration(seconds: 20),
    )..repeat();

    // Más burbujas para desktop
    final bubbleCount = widget.isDesktop ? 30 : 15;

    for (int i = 0; i < bubbleCount; i++) {
      bubbles.add(Bubble(
        size: Random().nextDouble() * (widget.isDesktop ? 50 : 30) + 10,
        x: Random().nextDouble(),
        y: Random().nextDouble(),
        speed: Random().nextDouble() * 0.5 + 0.1,
        opacity: Random().nextDouble() * 0.3 + 0.1,
      ));
    }
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: _controller,
      builder: (context, child) {
        return CustomPaint(
          painter: BubblePainter(bubbles, _controller.value),
        );
      },
    );
  }
}

class Bubble {
  double size;
  double x;
  double y;
  double speed;
  double opacity;
  double? initialY;

  Bubble({
    required this.size,
    required this.x,
    required this.y,
    required this.speed,
    required this.opacity,
  }) {
    initialY = y;
  }
}

class BubblePainter extends CustomPainter {
  final List<Bubble> bubbles;
  final double animationValue;

  BubblePainter(this.bubbles, this.animationValue);

  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()..style = PaintingStyle.fill;

    for (final bubble in bubbles) {
      // Calcular posición Y con animación
      final yPos = (bubble.initialY! + animationValue * bubble.speed) % 1.2;

      paint.color = Colors.white.withOpacity(bubble.opacity);

      canvas.drawCircle(
        Offset(bubble.x * size.width, yPos * size.height),
        bubble.size,
        paint,
      );
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => true;
}

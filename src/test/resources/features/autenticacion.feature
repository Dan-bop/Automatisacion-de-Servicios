Feature: Autenticación de usuarios
  Como usuario registrado
  Quiero iniciar sesión y consultar mi perfil
  Para acceder a mi información

  Background:
    Given que Danner tiene acceso al servicio de autenticación

  Scenario: Login exitoso devuelve un token
    When inicia sesión con usuario "emilys" y contraseña "emilyspass"
    Then el servicio responde con código 200
    And recibe un token de acceso

  Scenario: Consultar el perfil con el token obtenido
    When inicia sesión con usuario "emilys" y contraseña "emilyspass"
    And consulta su perfil con el token recibido
    Then el servicio responde con código 200
    And el usuario del perfil es "emilys"

  Scenario: Login con contraseña incorrecta es rechazado
    When inicia sesión con usuario "emilys" y contraseña "incorrecta123"
    Then el servicio responde con código 400
    And el mensaje de error contiene "Invalid credentials"

  Scenario: Validar usuario incorrecto en el perfil (fallo intencional)
    When inicia sesión con usuario "emilys" y contraseña "emilyspass"
    And consulta su perfil con el token recibido
    Then el servicio responde con código 200
    And el usuario del perfil es "usuario_inexistente"
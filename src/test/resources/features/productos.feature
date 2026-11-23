Feature: Gestión de productos
  Como usuario del servicio
  Quiero consultar, crear, actualizar y eliminar productos
  Para administrar el catálogo

  Background:
    Given que Danner tiene acceso al servicio de productos

  Scenario: Consultar un producto existente
    When consulta el producto con id 1
    Then el servicio responde con código 200
    And el producto devuelto tiene el id 1

  Scenario: Crear un producto nuevo
    When crea un producto llamado "Laptop Gamer" con precio 1500
    Then el servicio responde con código 201
    And el título del producto es "Laptop Gamer"

  Scenario: Actualizar un producto existente
    When actualiza el producto 1 con el título "Producto Editado"
    Then el servicio responde con código 200
    And el título del producto es "Producto Editado"

  Scenario: Eliminar un producto existente
    When elimina el producto con id 1
    Then el servicio responde con código 200
    And el producto aparece como eliminado

  Scenario: Consultar un producto que no existe
    When consulta el producto con id 99999
    Then el servicio responde con código 404
    And el mensaje de error contiene "not found"
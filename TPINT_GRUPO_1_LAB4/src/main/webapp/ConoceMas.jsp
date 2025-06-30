<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Conoce más sobre nosotros</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="includes/NavbarCliente.jsp" %>

    <div class="container my-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class="mb-0">
                Conoce más sobre nosotros
            </h4>
            <a href="InicioCliente.jsp" class="btn btn-primary">
                <i class="bi bi-arrow-left-circle me-1"></i> Volver
            </a>
        </div>
        <hr>

        <div class="row justify-content-center">
            <div class="col-lg-10 col-md-12">
                <div class="py-3">
                	<h1 class="text-center mb-4">Trabajo Práctico Integrador</h1>
                    <h2 class="text-center mb-4">Sistema de Gestión Bancaria</h2>
                    <p class="text-center lead">
                        Este proyecto fue desarrollado como parte del curso de <strong class="fw-bold">Laboratorio IV</strong>
                        de la <strong class="fw-bold">Tecnicatura Universitaria en Programación</strong>.
                    </p>
                </div>
            </div>
        </div>
        <hr class="my-4">

        <div class="row justify-content-center">
            <div class="col-lg-10 col-md-12">
                <h5 class=" fw-bold text-center mb-2">UNIVERSIDAD TECNOLÓGICA NACIONAL</h5>
                <p class="fw-bold text-center mb-5">FACULTAD REGIONAL GENERAL PACHECO</p>

                <h5 class="mt-4 mb-3 text-center">Datos del Curso y Equipo</h5>
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <ul class="list-group list-group-flush mb-4">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Curso:
                                <span class="fw-bold">LABORATORIO IV</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Carrera:
                                <span class="fw-bold">Tecnicatura Universitaria en Programación</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Profesora:
                                <span class="fw-bold">Tamara Herrera</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Grupo:
                                <span class="fw-bold">Grupo 1</span>
                            </li>
                        </ul>
                    </div>
                </div>

                <h5 class="mb-3 text-center">Integrantes del Equipo</h5>
                <div class="row justify-content-center">
                    <div class="col-md-8"> <ul class="list-group list-group-flush">
                            <li class="list-group-item text-center">Manuel Chique</li>
                            <li class="list-group-item text-center">Nicolás Martín Verón</li>
                            <li class="list-group-item text-center">Tobías Iaconis</li>
                            <li class="list-group-item text-center">Nicolás Julián Montesano</li>
                            <li class="list-group-item text-center">David Elías Ysaguirre</li>
                            <li class="list-group-item text-center">Pablo Coll Benegas</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
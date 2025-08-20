# Tokimon Card Server

## Overview

Tokimon Card Server is a robust Spring Boot REST API designed to manage collectible Tokimon creatures, inspired by Pokémon. This project demonstrates advanced backend engineering, RESTful API design, and professional software development practices. Built for scalability, reliability, and maintainability, it showcases skills highly valued in modern enterprise environments.

## Features

- **Full CRUD API**: Create, Read, Update, and Delete Tokimon cards via RESTful endpoints
- **Persistent Storage**: Data is stored in JSON files for easy portability and integration
- **Thread-Safe ID Generation**: Ensures unique identifiers even in concurrent environments
- **Comprehensive Testing**: Integration tests using Spring Boot and MockMvc for reliability
- **Modern Java & Spring Boot**: Utilizes Java 22 and Spring Boot 3.x for cutting-edge performance
- **Professional Documentation**: Well-commented code and clear API structure

## Technologies Used

- Java 22
- Spring Boot 3.x
- Maven
- Gson (JSON serialization)
- JUnit & MockMvc (Testing)

## API Endpoints

- `GET /tokimon/all` — Retrieve all Tokimons
- `GET /tokimon/{tid}` — Get a specific Tokimon by ID
- `POST /tokimon/add` — Add a new Tokimon
- `PUT /tokimon/edit/{tid}` — Edit an existing Tokimon
- `DELETE /tokimon/{tid}` — Delete a Tokimon

## Data Model

Each Tokimon card includes:

- `tid`: Unique ID
- `name`: Tokimon name
- `imagePath`: Image URL
- `type`: Element type (e.g., Electric, Water)
- `rarityScore`: Rarity indicator

## Checkout Tokimon Client
[Tokimon Client](https://github.com/sgoyal04/tokimon-card-Client)

## Contact

Created by Sanika and Gurveen.

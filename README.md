# GastroLab

Este projeto foi criado como uma atividade acadêmica para a **FIAP**. O objetivo é desenvolver uma aplicação móvel e uma aplicação web que funcionem de forma integrada.

Por questões de organização esse projeto foi dividido em dois respositórios:

 - [Web](https://github.com/lazarusms/gastrolab)
 - [Mobile](https://github.com/lazarusms/gastrolab-mobile)

### 💻 O projeto

Este projeto é uma solução para reservas de clientes em um restaurante.

- A aplicação móvel permite que os clientes façam suas reservas.
- As reservas criadas são enviadas para a API.
- No site, as reservas podem ser gerenciadas, permitindo a confirmação ou o cancelamento.
   
### 🎲 Rodando o Web 
![image](https://github.com/lazarusms/gastrolab/assets/107807531/2e4282c0-3cf3-4f56-a962-d99f9933c18d)

```bash
# Clone este repositório
$ git clone <https://github.com/lazarusms/gastrolab>

# Acesse a pasta do projeto no terminal/cmd
$ cd gastrolab

# Build o projeto [Docker] 
$ make build

# Execute a aplicação [Docker]
$ make run

# O servidor inciará na porta:8080 - acesse <http://localhost:8080/index.html>

```

### 📱 Rodando o Mobile 

```bash
# Clone este repositório
$ git clone <https://github.com/lazarusms/gastrolab-mobile>

# Acesse a pasta do projeto no terminal/cmd
$ cd gastrolab-mobile

# Rode o emulador
# A maneira mais simples de fazer isso é executando o projeto no Android Studio
# O projeto é configurado para se conectar ao localhost do emulador
```

<div align="center">
  <img src="https://github.com/lazarusms/gastrolab-mobile/assets/107807531/885bcd46-6a4e-46d7-b429-1eb1e38f861f" alt="image">
</div>


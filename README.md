<h1 align="center">Servidor para Loja Online - concurrent-store 🛍🛒</h1>

## Índice 
* [Passo a Passo - Como executar?](#Passo-a-passo-para-inicializar)
* [Inicial](#Clonar-o-repositório)
* [Clone do Repositóriio](#Clonar-o-repositório)
* [Instalação](#Instalação)
* [Subir o Servidor](#Subir-o-servidor)
* [Execução dos Testes](#Testes)

---
## Passo a passo para inicializar
Passo a passo de como instalar e executar o sistema.

#### **1️⃣ Clonar o repositório** 
```sh
git clone https://github.com/AndreFelipeAlmeida/concurrent-store
cd concurrent-store
```
---
### 🚀 Instalação 

#### **2️⃣ Instale o Maven e a JDK**  
[**Maven:**](https://maven.apache.org/install.html)
```sh
# LINUX
    sudo apt install maven

# WINDOWS (Com Chocolatey)
    choco install maven
```

[**JDK 21**](https://www.oracle.com/br/java/technologies/downloads/#jdk21-linux)
```sh
Acesse o link acima para baixar o JDK 21.
```

Verifique se eles foram baixados:
```sh
mvn --version
java --version
```

#### **3️⃣ Instalar dependências**  
```sh
mvn clean install
```
---

#### **4️⃣ Subir o servidor**  
```sh
mvn spring-boot:run
```
---

### 👩‍💻 Testes
Passo a passo de como rodar os testes.

#### **1️⃣ Suba o servidor** 
```sh
mvn spring-boot:run
```
---
#### **2️⃣ Rode o comando de testes para o maven**
```sh
mvn test
```
---

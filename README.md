# concurrent-store

---
## 🚀 Instalação  

Passo a passo de como instalar e executar o sistema.  

### **1️⃣ Clonar o repositório**  
```sh
git clone https://github.com/AndreFelipeAlmeida/concurrent-store
cd concurrent-store
```

### **2️⃣ Instale o Maven e a JDK**  
[**Maven:**](https://maven.apache.org/install.html)
```sh
# LINUX
    sudo apt install maven
# WINDOWS (Com Chocolatey)
    choco install maven
```

[**JDK 21**](https://www.oracle.com/br/java/technologies/downloads/#jdk21-linux)

Verifique se eles foram baixados
```sh
mvn --version
java --version
```

### **3️⃣ Instalar dependências**  
```sh
mvn clean install
```

### **4️⃣ Suba o servidor**  
```sh
mvn spring-boot:run
```

---
# alura-food-microservices

## Microsserviços na prática: implementando com Java e Spring

- Arquitetura:

![image](https://user-images.githubusercontent.com/61791877/205171850-9c567eb5-1cc6-496e-8dbb-6c4d74ec17ca.png)

---

- Uso de migrations utilizando o Flyway

Padrão para criar arquivo:

```
V<numero da versão>__<nome do comando>.sql
V1__criar_tabela_pagamentos.sql
```

---

- Service discovery server(service registry)

Adicionar dependência do maven

Configurações iniciais no application.properties

```
server.port=8081

spring.application.name=server
eureka.client.register-with-eureka=false # a app será um server, logo a mesma não se registra como client
eureka.client.fetch-registry=false
eureka.client.serviceUrl.defaultZone=http://localhost:8081/eureka
```

Em Application, inserir

```
@EnableEurekaServer
```

- Service discovery client(self registration)

```
@EnableEurekaClient
```

Adicionar dependência do maven

Configurações iniciais no application.properties

```
spring.application.name=pagamentos-ms
eureka.client.serviceUrl.defaultZone=http://localhost:8081/eureka
server.port=0
```

- Gateway

Adicionar dependência do maven para o gateway

Adicionar dependência do maven para o registro desse microservice

Configurações iniciais no application.properties

```
server.port=8082

eureka.client.serviceUrl.defaultZone=http://localhost:8081/eureka

spring.application.name=gateway
spring.cloud.gateway.discovery.locator.enabled=true # config. do gateway para descoberta
spring.cloud.gateway.discovery.locator.lowerCaseServiceId=true  # definição do nome dos microservices para que sejam em letra minúscula
```

- Service discovery client(self registration)

```
@EnableEurekaClient
```

- Para acessar os endpoints de qualquer um dos microservices, basta seguir o padrão

http://localhost:porta-definida-gateway/nome-do-microservice/endpoint...

- Escalabilidade horizontal com Load Balancer

Definir o identificador único da instância

```
eureka.instance.instance-id=${spring.application.name}:${random.int}
```

Em controller, retornar a porta em que a instância está rodando atualmente

```
@GetMapping("/porta")
public String retornaPorta(@Value("${local.server.port}") String porta){
    return String.format("Requisição respondida pela instância executando na porta %s", porta);
}
```

Para rodar várias instâncias da mesma aplicação, basta abrir o terminal da IDE e digitar

```
& "c:\alura-food\pedidos\mvnw.cmd" spring-boot:run -f "c:\alura-food\pedidos\pom.xml" (caminho da aplicação)
```

Para testar se o load balancer funciona, basta realizar várias requisições

```
http://localhost:porta-definida-gateway/nome-do-microservice/porta
```

---


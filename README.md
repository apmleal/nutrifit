# 🥗🏋️ NutriFit – Plataforma SaaS de Nutrição e Treinamento

**NutriFit** é uma plataforma **SaaS multi-tenant** para gestão de **dietas, treinos e acompanhamento de usuários**, permitindo que **nutricionistas, personal trainers, academias e clínicas** ofereçam planos personalizados aos seus alunos/pacientes.

O sistema é **modular, escalável e comercialmente orientado**, com **planos, feature flags e limites por assinatura**, pronto para evoluir para billing real.

---

## 🎯 Objetivo do Projeto

Criar uma API moderna em **Java + Spring Boot** para:
- Cadastro e gestão de **dietas alimentares**
- Cadastro e gestão de **treinos físicos**
- Vínculo de usuários a **nutricionistas** e **personal trainers**
- Comercialização via **SaaS**, com venda por módulos e quantidade de usuários

---

## 🧩 Modelo SaaS

### Multi-Tenant
- Cada cliente (academia, clínica ou profissional) é um **Tenant**
- Isolamento lógico via `tenant_id`
- Um tenant possui **uma assinatura ativa**

### Venda por Módulos
- 🥗 Módulo Nutricional
- 🏋️ Módulo de Treinos

### Venda por Limites
- Quantidade de usuários
- Quantidade de dietas
- Quantidade de treinos

---

## 🚀 Funcionalidades

### 👤 Usuários
- Admin do tenant
- Nutricionista
- Personal Trainer
- Aluno / Paciente

### 🥗 Nutrição
- Cadastro de alimentos
- Cadastro de porções
- Criação de dietas
- Refeições e itens alimentares
- Associação dieta → usuário
- Associação dieta → nutricionista

### 🏋️ Treinos
- Cadastro de exercícios
- Criação de treinos
- Séries, repetições e descanso
- Associação treino → usuário
- Associação treino → personal trainer

### 💰 SaaS / Comercial
- Planos de assinatura
- Feature flags dinâmicas
- Limites por plano
- Upgrade e downgrade
- Controle de acesso por plano

---

## 🛡️ Segurança e Controle de Acesso

- **Spring Security**
- RBAC (Role-Based Access Control)
- Feature Flags por plano
- Limites aplicados via **Spring AOP**

Exemplo:
```java
@PreAuthorize("hasRole('NUTRICIONISTA')")
@RequiresFeature("NUTRITION_MODULE")
@RequiresLimit("MAX_DIETS")
public void criarDieta() {}
```

---

## 🧠 Arquitetura

### Estilo Arquitetural
- **Modular Monolith** (evoluível para microsserviços)
- DDD leve
- Clean Architecture

### Diagrama de Arquitetura (Visão Lógica)

```text
┌────────────────────────────┐
│        Clientes            │
│ Web / Mobile / Admin       │
└─────────────┬──────────────┘
              │ HTTP / REST
┌─────────────▼──────────────┐
│        Spring Boot API     │
│        (NutriFit)          │
│                            │
│ ┌────────────────────────┐ │
│ │  Spring Security       │ │
│ │  RBAC + JWT            │ │
│ └──────────┬─────────────┘ │
│            │                │
│ ┌──────────▼─────────────┐ │
│ │  Tenant Context        │ │
│ │  (Multi-Tenant)        │ │
│ └──────────┬─────────────┘ │
│            │                │
│ ┌──────────▼─────────────┐ │
│ │  SaaS Module            │ │
│ │  - Plan                 │ │
│ │  - Feature Flags        │ │
│ │  - Limits               │ │
│ └──────────┬─────────────┘ │
│            │                │
│ ┌──────────▼─────────────┐ │
│ │  Business Modules       │ │
│ │  - Nutrition            │ │
│ │  - Workout              │ │
│ │  - Users                │ │
│ └──────────┬─────────────┘ │
│            │                │
│ ┌──────────▼─────────────┐ │
│ │  Persistence            │ │
│ │  JPA / Hibernate        │ │
│ └──────────┬─────────────┘ │
└─────────────▼──────────────┘
              │
     ┌────────▼────────┐
     │  PostgreSQL     │
     │  (tenant_id)   │
     └────────────────┘
```

---

### Separação por Módulos
```text
subscription   → SaaS / Planos
nutrition      → Dietas e alimentos
workout        → Treinos
user           → Usuários e perfis
auth           → Autenticação e autorização
tenant         → Contexto multi-tenant
shared         → Código compartilhado
```

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21**
- **Spring Boot 3**
- Spring Web
- Spring Data JPA
- Spring Security
- Spring AOP

### Banco de Dados
- PostgreSQL
- UUID como chave primária
- Flyway para versionamento

### Documentação
- OpenAPI / Swagger

### Infraestrutura (futuro)
- Docker
- Kubernetes
- Observabilidade (Micrometer + Prometheus)

---

## 📦 Modelo de Planos (Exemplo)

| Plano | Nutrição | Treinos | Usuários |
|-----|--------|---------|---------|
| Free | ❌ | ❌ | 5 |
| Nutri | ✅ | ❌ | 50 |
| Pro | ✅ | ✅ | 200 |
| Enterprise | ✅ | ✅ | Ilimitado |

---

## 🔄 Roadmap

### Fase 1 – MVP
- [x] Modelagem de domínio
- [x] Módulo SaaS
- [ ] CRUD de usuários
- [ ] CRUD de dietas
- [ ] CRUD de treinos

### Fase 2 – SaaS Completo
- [ ] Feature flags dinâmicas
- [ ] Limites por plano
- [ ] Upgrade / downgrade

### Fase 3 – Comercial
- [ ] Billing (Stripe / Pagar.me)
- [ ] Trials
- [ ] Cupons
- [ ] Add-ons

---

## 🧪 Qualidade
- Testes unitários
- Testes de integração
- Validação de regras de negócio

---

## 📌 Status do Projeto

🚧 Em desenvolvimento – foco em arquitetura sólida e escalável.

---

## 🤝 Contribuição

Este projeto tem fins educacionais e evolutivos, mas foi pensado desde o início para **uso real em produção**.

---

## 📄 Licença

Definir (MIT / Apache 2.0)


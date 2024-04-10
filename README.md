# Adevspoon Backend
문답을 통해 쉽고 간편하게 CS(Computer Science) 공부를 도와주는 모바일 어플리케이션 '개발 한 스푼'의 서버 어플리케이션입니다.

(총 1만 다운로드 이상, 최대 하루 이용자 수 300명)

### Product
- [iOS 앱스토어](https://apps.apple.com/kr/app/%EA%B0%9C%EB%B0%9C-%ED%95%9C-%EC%8A%A4%ED%91%BC/id1638716398)  
- [Android 플레이스토어](https://play.google.com/store/apps/details?id=com.adevspoon.adevspoon&hl=ko&gl=US)

### Dev Log
- [AWS 서비스를 로컬, 테스트 환경에서 사용하기(ft. LocalStack)]()
- [ControllerAdvice로 공통 응답 형식 다루기]()
- [세밀하게 예외 다루기(ft. ResponseEntityExceptionHandler)]()
- [어노테이션만으로 Security 설정 off 시키기(Swagger에도 표시하기)]()
- [요청, 응답, Entity에 포함된 Legacy Enum 효율적으로 관리하기]()

<br />

## 개발환경
### 🔨 Tech Stack
*기존 서버리스 아키텍쳐(AWS APIGateway, Lambda)에서 Spring Boot로 마이그레이션*  
- Spring Boot, Kotlin, Docker, JPA, MySQL, AWS
- 로컬&테스트 한정 - JUnit, Mockk, TestContainer, LocalStack

<br />

### ⚖️ Project Structure
멀티 모듈 구조로 외부 시스템과의 느슨한 연결에 집중하여 패키지를 분리.  
환경설정 정보는 git submodule로 pivate 레포지토리에서 따로 관리하고 gradle build시 fetch하도록 설정.
```bash
├── adevspoon-api     ### API 모듈 ###
│    ├── common  # 전역적으로 사용되는 컴포넌트  
│    ├── config  # 전역적으로 적용되는 설정
│    └── <API 그룹>
│        ├── controller
│        ├── dto
│        └── service 
│
├── adevspoon-common  ### 공통 모듈 ### (exception, enum,dto) 
├── adevspoon-domain  ### 도메인 모듈 ###     
│    ├── common  # 도메인 전역적으로 사용되는 컴포넌트
│    ├── config  # 도메인 전역적으로 적용되는 설정
│    └── <도메인> 
│        ├── domain
│        ├── dto
│        ├── exception
│        ├── repository
│        └── service
│
└── adevspoon-infrastructure  ### 외부 시스템 모듈 ### 
     ├── common  # 인프라 전역적으로 사용되는 컴포넌트
     ├── config  # 인프라 전역적으로 적용되는 설정
     └── <외부 시스템>  # Oauth, Storage 등 
         ├── dto
         ├── exception
         ├── service # interface와 구현체를 분리하여 외부 시스템의 변경에 유연하게 대응
         └──         # client, config, util 등 필요에따라 패키지 분리 
```

<br />

### 🪜 Infra Structure
AWS & Terraform 기반으로 배포. 프리티어 내에서 최대한 비용 효율적으로 구성

<details>
<summary>(과거) Serverless 기반</summary>
<div markdown="1">

![Serverless](https://github.com/kids-ground/adevspoon-backend/assets/52196792/0a0a9e95-64c0-4280-b552-3a1017d80d5c)
</div>
</details>

<details>
<summary>(현재) EC2 기반</summary>
<div markdown="1">

(작성중)

</div>
</details>


<details>
<summary>CI/CD</summary>
<div markdown="1">

1. Github Push
2. Github Actions 동작 -> ECR Push
3. EventBridge로 CodePipeline 트리거
4. CodeDeploy로 블루/그린 배포

</div>
</details>

<br />

## Rule
### 📎 Issue & Branch & Commit
- **Issue**
  - `feature` : 새로운 기능 추가
  - `bugfix` : 버그 수정용
  - `hotfix` : 긴급 수정용
- **Branch**
  - `main` : 배포 가능한 상태의 코드만 merge
  - `develop` : 기준 브랜치
  - `feat/#{iusse}` : (기능 개발) 이슈번호 기준으로 생성 
  - `bugfix/#{issue}` : (버그 수정) 
  - `hotfix/#{issue}` : (긴급 수정) main 브랜치에서 생성
- **Commit** 
  - `{깃모지} 메세지` : Gitmoji 기반으로 메시지 작성

<br />

### 🖇️ Pull Request
PR Template 기반으로 작성
- PR Title - `Feat#{issue}. 내용`
- 본문
  - Issue : `close #issue번호`로 이슈 연결시키기
  - Summary : 기능/버그 요약
  - Description : 설명이 필요한 부분 작성
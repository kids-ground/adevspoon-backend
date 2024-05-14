# Adevspoon Backend
문답을 통해 쉽고 간편하게 CS(Computer Science) 공부를 도와주는 모바일 어플리케이션 '개발 한 스푼'의 백엔드 레포지토리  
*(앱 다운로드 수 1만+, 최대 하루 이용자 수 300+)*

1. [기술스택](#기술스택)
2. [인프라](#인프라)
3. [CI/CD & 자동화](#cicd--자동화)
4. [History](#history)

<br />

## 기술스택
*서버리스 아키텍쳐(AWS API Gateway + Lambda) -> Spring Boot 마이그레이션*  
Spring Boot, Kotlin, JPA, MySQL, LocalStack, AWS, Terraform, Github Actions

<br />

## 인프라
AWS & Terraform 기반 배포. 비용 효율적으로 구성
- 구조 : ALB -> EC2 -> RDS (3티어 아키텍쳐)
- 배포 : Github Actions + CodeDeploy + ALB를 통한 무중단(블루/그린) 배포

![adevspoon-v1](https://github.com/kids-ground/adevspoon-backend/assets/52196792/c3c5eef5-f6b9-4352-bcb3-6454397fc193)

<br />

## CI/CD & 자동화
브랜치 & PR 기반 자동화. 무중단 배포 파이프라인 구축  

![adevspoon-automation](https://github.com/kids-ground/adevspoon-backend/assets/52196792/957ffd4d-9daa-499f-91e7-d68e2be5dca6)

1. `create-release-branch` : 최신 Tag 기반으로 자동 버전업된 release 브랜치 생성
2. `create-hotfix-branch` : 최신 Tag 기반 hotfix 브랜치 생성
3. `deploy-dev` : release & hotfix 내 push 시 dev 환경 자동배포
4. `tagging-and-release` : release & hotfix -> develop PR 머지 시 Tag/Release 생성
5. `deploy-prod` : Tag 생성 시 prod 환경 자동배포

<br />

## History
**Product**
- [iOS 앱스토어](https://apps.apple.com/kr/app/%EA%B0%9C%EB%B0%9C-%ED%95%9C-%EC%8A%A4%ED%91%BC/id1638716398)
- [Android 플레이스토어](https://play.google.com/store/apps/details?id=com.adevspoon.adevspoon&hl=ko&gl=US)

**Tech Log**
- [AOP를 이용해 이벤트 발행, 비동기로 이벤트 처리하기](https://theliar.tistory.com/11)
- [MySQL로 분산락 처리하기, 확장성/비용 효율적으로 동시성 관리하기](https://theliar.tistory.com/10)
- [늘어나는 예외 정보 확장성/가독성 좋게 관리하기](https://theliar.tistory.com/8)
- [어노테이션으로 API별 인증 해제시키고 가독성/개발생산성 높이기](https://theliar.tistory.com/6)
- [동일한 API 응답형식 공통처리하여 개발 생산성 높이기](https://theliar.tistory.com/5)
- [서버 마이그레이션, 요청 및 응답에 포함된 Legacy Enum 효율적으로 관리하기](https://theliar.tistory.com/1)

**Repository**
- Mobile Repository 
- Infra Repository
- [Wiki](https://github.com/kids-ground/adevspoon-backend/wiki)
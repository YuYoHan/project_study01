# project_study1
프로젝트 연습 

사용 기술 :
- Spring Boot <br/>
- Spring Security <br/>
- Spring Oauth2 <br/>
- Spring Batch <br/>
- JWT <br/>
- JPA <br/>
- JPQL <br/>
- Querydsl <br/>
- MySQL <br/>
- Swagger <br/>

## 목적
프로젝트 연습입니다. REST 방식으로 진행하며 일반 로그인을 했을 때 JWT 반환과 소셜 로그인을 했을 떄 서버에서 JWT를 반환해서 프론트에서 access token을 header에 넣어서 보내주면서 요청을 보내는 형태로 로직을 구성했습니다. 
<br />
<br />
JWT를 반환할 때 DTO에 담아서 보내주는데 DTO에 포함된 정보 :
- grantType <br />
- accessToken <br />
- refreshToken <br />
- userId <br />
- nickName <br />
- userEmail <br />
- accesstokenTime <br />
- refreshTokenTime <br />


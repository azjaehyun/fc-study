##
```
채용사이트의 데이터베이스 설계를 그래픽으로 표현하기 위해 ERD(개체-관계 다이어그램)를 사용하겠습니다. 아래의 그림은 일반적인 채용사이트의 ERD 예시입니다.


               +-------------------+
               |      Company      |
               +-------------------+
               | id (PK)           |
               | name              |
               | location          |
               | industry          |
               | website           |
               +-------------------+
                      |
                      |
                      |
               +-------------------+
               |     JobSeeker     |
               +-------------------+
               | id (PK)           |
               | name              |
               | email             |
               | phone             |
               | experience        |
               +-------------------+
                      |
                      |
                      |
               +-------------------+
               |        Job        |
               +-------------------+
               | id (PK)           |
               | title             |
               | description       |
               | requirements      |
               | company_id (FK)   |
               +-------------------+
                      |
                      |
                      |
               +-------------------+
               |     Application   |
               +-------------------+
               | id (PK)           |
               | job_id (FK)       |
               | jobseeker_id (FK) |
               | status            |
               +-------------------+
               
위의 ERD를 보면 다음과 같은 엔티티와 관계를 포함하고 있습니다:

Company: 회사 정보를 저장하는 엔티티입니다. 회사의 고유 ID(id), 이름(name), 위치(location), 산업(industry), 웹사이트(website) 등의 속성을 포함합니다.
JobSeeker: 구직자 정보를 저장하는 엔티티입니다. 구직자의 고유 ID(id), 이름(name), 이메일(email), 전화번호(phone), 경력(experience) 등의 속성을 포함합니다.
Job: 구인 정보를 저장하는 엔티티입니다. 구인 공고의 고유 ID(id), 제목(title), 설명(description), 요구 사항(requirements) 등의 속성을 포함합니다. 회사와의 관계를 나타내기 위해 company_id 외래 키를 사용합니다.
Application: 구직자가 구인 공고에 지원하는 정보를 저장하는 엔티티입니다. 지원 정보의 고유 ID(id), 구인 공고와 구직자와의 관계를 나타내는 외래 키인 job_id와 jobseeker_id, 그리고 상태(status) 등의 속성을 포함합니다.
이것은 채용사이트의 데이터베이스 설계를 간략히 나타내는 예시이며, 실제 구현에 따라 추가적인 엔티티와 관계가 필요할 수 있습니다.

```

## read me
https://jhipster.ddocs.cn/jdl/


-- Member Data
insert into member(id, nickname, name, sms_agreement, birth_date, contact, created_date, email, gender, is_deleted, sns) values (
    null, 'jeongwoolim', '임정우', true, '1991-03-01', '01066075331', '2021-02-23', 'jwlimtest@gmail.com', 'MALE', false, 'KAKAO'
);
insert into member(id, nickname, name, sms_agreement, birth_date, contact, created_date, email, gender, is_deleted, sns) values (
    null, 'jaeboklee', '이재복', false, '1993-12-25', '01099535609', '2021-03-14', 'jbleetest@gmail.com', 'MALE', false, 'NAVER'
);

-- Board Data
insert into board(id, capacity, category, contact, details, start_date, end_date, created_date, city, gu, member_id)
values (null , 3, 'TOWN', '01011112222', '1. 게시판 테스트입니다', '2021-05-22', '2021-06-22', null, '서울시', '강동구', 1);

insert into board(id, capacity, category, contact, details, start_date, end_date, created_date, city, gu, member_id)
values (null , 5, 'TOWN', '01022223333', '2. 게시판 테스트입니다', '2021-05-25', '2021-06-25', null, '서울시', '송파구', 1);

insert into board(id, capacity, category, contact, details, start_date, end_date, created_date, city, gu, member_id)
values (null , 7, 'TOWN', '01033334444', '3. 게시판 테스트입니다', '2021-08-22', '2021-10-22', null, '서울시', '강동구', 2);

insert into board(id, capacity, category, contact, details, start_date, end_date, created_date, city, gu, member_id)
values (null , 12, 'TOWN', '01044445555', '4. 게시판 테스트입니다', '2021-11-22', '2021-12-22', null, '경기도', '양평군', 2);

-- Volunteer Date
INSERT INTO volunteer(id, capacity, contact, created_date, details, start_date, end_date, gender, city, gu, location, manager,
                      time, title)
values (1, 4, '010-9953-5609', '20210501', '상세', '20210510', '20210512', 'ALL', '서울특별시', '마포구', '마포아트센터', '이재복', '오전9시', '제목');
INSERT INTO volunteer(id, capacity, contact, created_date, details, start_date, end_date, gender, city, gu, location, manager,
                      time, title)
values (2, 4, '010-9953-5609', '20210501', '상세', '20210512', '20210514', 'ALL', '서울특별시', '서대문구', '서대문센터', '이재복', '오전10시', '제목');
INSERT INTO volunteer(id, capacity, contact, created_date, details, start_date, end_date, gender, city, gu, location, manager,
                      time, title)
values (3, 4, '010-9953-5609', '20210501', '상세', '20210514', '20210516', 'ALL', '서울특별시', '동작구', '동작센터', '이재복', '오전11시', '제목');

insert into managerinfo (managername, managerid, managerpwd) values ('엄지존', 'admin', 'admin')
insert into managerinfo (managername, managerid, managerpwd) values ('매니저', 'admin2', 'admin2') 

insert into userinfo (username, useremail, userid, userpwd) values ('엄지존', 'johnny@gmail.com', 'johnny1234', '1234')
insert into userinfo (username, useremail, userid, userpwd) values ('손흥민', 'son@gmail.com', 'son', '1234')


insert into fooditem (foodname, foodprice, status, stock) values ('더블 비프 불고기 버거', 5900, 1, 35)
insert into fooditem (foodname, foodprice, status, stock) values ('치즈 와퍼', 5400, 1, 20)
insert into fooditem (foodname, foodprice, status, stock) values ('몬스터 와퍼', 5000, 1, 10)
insert into fooditem (foodname, foodprice, status, stock) values ('트러플 머쉬룸 와퍼 주니어', 6400, 1, 80)
insert into foodimgfile (imgfilename, mid, orgfilename, fooditem_fid) values ('더블비프불고기.png', 1, 'double_biff.png', 1)
insert into foodimgfile (imgfilename, mid, orgfilename, fooditem_fid) values ('치즈와퍼.png', 2, 'cheese.png', 2)
insert into foodimgfile (imgfilename, mid, orgfilename, fooditem_fid) values ('몬스터와퍼.png', 1, 'monster.png', 3)
insert into foodimgfile (imgfilename, mid, orgfilename, fooditem_fid) values ('트러플머쉬룸와퍼주니어.png', 1, 'triple.png', 4)
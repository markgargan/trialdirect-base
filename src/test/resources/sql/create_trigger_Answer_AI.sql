create trigger Answer_AI after INSERT on Answer for each row begin insert into Answer_audit(id, sortOrder, answerText, action, dbUser, appUser, createdTs) values(new.id, new.sortOrder, new.answerText, 'INSERT', user(), null, CURRENT_TIMESTAMP()); end;
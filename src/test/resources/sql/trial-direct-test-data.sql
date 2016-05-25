
INSERT INTO `TherapeuticArea` VALUES (1, 1, 'Cancer');

--INSERT INTO `TherapeuticArea` VALUES (1, 1, 'Lung Cancer',1);
--INSERT INTO `TherapeuticArea` VALUES (2, 2, 'Skin Cancer', 1);


INSERT INTO `Answer` VALUES (1, 1, 'Stomach');
INSERT INTO `Answer` VALUES (2, 2, 'Skin');
INSERT INTO `Answer` VALUES (3, 3, 'Lungs');
INSERT INTO `Answer` VALUES (4, 4, 'Larynx');
INSERT INTO `Answer` VALUES (5, 5, '10 or more');
INSERT INTO `Answer` VALUES (6, 6, '3-5');
INSERT INTO `Answer` VALUES (7, 7, '5-10');
INSERT INTO `Answer` VALUES (8, 8, '0-3');
INSERT INTO `Answer` VALUES (9, 9, '70+');
INSERT INTO `Answer` VALUES (10, 10, '0-18');
INSERT INTO `Answer` VALUES (11, 11, '19-35');
INSERT INTO `Answer` VALUES (12, 12, '35-70');

INSERT INTO `Question` VALUES (1, 1, 'What is the type of your cancer?');
INSERT INTO `Question` VALUES (2, 2, 'What is your age?');
INSERT INTO `Question` VALUES (3, 3, 'How long do you suffer from cancer (in year)?');

INSERT INTO `QuestionnaireEntry` VALUES (1,1,1);
INSERT INTO `QuestionnaireEntry` VALUES (2,3,1);
INSERT INTO `QuestionnaireEntry` VALUES (3,2,1);

INSERT INTO `Trial` VALUES (1, 1,'Lung Cancer Trial', 'TC_456',1);

INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (3,1,1,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (8,2,1,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (4,3,1,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (9,4,1,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (5,9,2,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (1,10,2,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (2,11,2,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (12,12,2,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (10,5,3,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (7,6,3,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (11,7,3,1);
INSERT INTO `TrialSelectorQuestionnaireEntry` VALUES (6,8,3,1);

INSERT INTO `User` VALUES (1,'Robert',1);
INSERT INTO `UserSelectorQuestionnaireEntry` VALUES (2,11,2,1,1);
INSERT INTO `UserSelectorQuestionnaireEntry` VALUES (1,7,3,1,1);

INSERT INTO `hibernate_sequences` VALUES ('TrialSelectorQuestionnaireEntry',1);
INSERT INTO `hibernate_sequences` VALUES ('UserSelectorQuestionnaireEntry',1);

INSERT INTO `questionnaireEntry_answer` VALUES (1,1);
INSERT INTO `questionnaireEntry_answer` VALUES (1,2);
INSERT INTO `questionnaireEntry_answer` VALUES (1,3);
INSERT INTO `questionnaireEntry_answer` VALUES (1,4);
INSERT INTO `questionnaireEntry_answer` VALUES (2,5);
INSERT INTO `questionnaireEntry_answer` VALUES (2,6);
INSERT INTO `questionnaireEntry_answer` VALUES (2,7);
INSERT INTO `questionnaireEntry_answer` VALUES (2,8);
INSERT INTO `questionnaireEntry_answer` VALUES (3,9);
INSERT INTO `questionnaireEntry_answer` VALUES (3,10);
INSERT INTO `questionnaireEntry_answer` VALUES (3,11);
INSERT INTO `questionnaireEntry_answer` VALUES (3,12);


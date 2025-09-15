-- 각 서비스별 전용 사용자 생성
CREATE USER IF NOT EXISTS 'user'@'%' IDENTIFIED BY 'user';
CREATE USER IF NOT EXISTS 'board'@'%' IDENTIFIED BY 'board';
CREATE USER IF NOT EXISTS 'comment'@'%' IDENTIFIED BY 'comment';

-- 권한 부여
GRANT ALL PRIVILEGES ON user_service_db.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON board_service_db.* TO 'board'@'%';
GRANT ALL PRIVILEGES ON comment_service_db.* TO 'comment'@'%';

FLUSH PRIVILEGES;
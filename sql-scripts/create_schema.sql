USE comicBook_DB;

CREATE TABLE IF NOT EXISTS comic_Issue(
    Comic_ID INT NOT NULL,
    Series_Name VARCHAR(255) NOT NULL,
    Series_Year CHAR(4) NOT NULL,
    issue_ID VARCHAR(4) NOT NULL,
    collect_ID INT NULL,
    PRIMARY KEY (Comic_ID),
    CONSTRAINT uq_Issue UNIQUE (Series_Name, Series_Year, issue_ID)   
);
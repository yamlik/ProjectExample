CREATE OR ALTER FUNCTION [dbo].[UFN_WaitDelay_Slow]()RETURNS NVARCHAR(10) AS BEGIN DECLARE @dtStart DATETIME=GETUTCDATE()
WHILE DATEDIFF(SECOND,@dtStart,GETUTCDATE()) < 2 BEGIN  SET @dtStart = @dtStart END  RETURN 'TEST' END;

CREATE OR ALTER FUNCTION [dbo].[UFN_WaitDelay_Cautious] () RETURNS NVARCHAR(10) AS BEGIN DECLARE @dtStart DATETIME=GETUTCDATE()
WHILE DATEDIFF(MILLISECOND,@dtStart,GETUTCDATE())<400 BEGIN SET @dtStart = @dtStart END RETURN 'TEST' END;

CREATE OR ALTER FUNCTION [dbo].[UFN_WaitDelay_ATSNTptable] () RETURNS NVARCHAR(10) AS BEGIN DECLARE @dtStart DATETIME=GETUTCDATE()
WHILE DATEDIFF(MILLISECOND,@dtStart,GETUTCDATE()) < 10 BEGIN SET @dtStart = @dtStart END RETURN 'TEST' END;

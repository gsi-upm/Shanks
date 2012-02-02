coworker(resolverAgent1).

+failures(A) : A>5 <- +repair.
+repair : myself(I) <- fix(I); -repair.
+help(C)[source(A)] : myself(I) & (C == I) <- ?coworker(F); fix(I); .send(F,tell,done(I)); -help(C)[source(A)].
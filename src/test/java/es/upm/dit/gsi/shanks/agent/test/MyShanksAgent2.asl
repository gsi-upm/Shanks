coworker(resolverAgent1).

+failures(A) : A==many <- +repair.
+repair : myself(I) <- fix(I); -repair.
+help(C,S)[source(A)] : myself(I) & (C == I) <- fix(I); .send(S,tell,done(I)); -help(C,S)[source(A)].
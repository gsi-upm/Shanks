coworker(resolverAgent2).

+failures(A) : not waiting(C) & A>0 <- +ask.
+failures(A) : A>4 <- +repair.
+repair : myself(I) <- fix(I); -repair.
+ask : coworker(C) & not waiting(C) & myself(I) & not(I == C) <- +waiting(C); .send(C,tell,help(C,I)); -ask.
+done(C)[source(B)] : waiting(C) <- -waiting(C); -done(C)[source(B)].
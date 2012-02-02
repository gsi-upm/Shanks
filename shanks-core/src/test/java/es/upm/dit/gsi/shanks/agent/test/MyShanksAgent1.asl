coworker(resolverAgent2).

+failures(A) : not waiting(C) & A==few <- +ask.
+failures(A) : A==many <- +repair.
+repair : myself(I) <- fix(I); -repair.
+ask : coworker(C) & not waiting(C) & myself(I) & not(I == C) <- +waiting(C); .send(C,tell,help(C,I)); -ask.
+done(C)[source(B)] : waiting(C) <- -waiting(C); -done(C)[source(B)].
coworker(resolverAgent2).

+failures(A) : not waiting(C) & A==few <- -+ask.
+failures(A) : not waiting(C) & A==many <- -+repair.
+repair : myself(I) <- fix(I).
+ask : coworker(C) & not waiting(C) & myself(I) & not(I == C) <- +waiting(C); .send(C,tell,help(C,I)).
+done(C)[source(B)] : waiting(C) <- -waiting(C); .print("RESOLVED"); -done(C)[source(B)].
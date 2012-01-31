+repair : myself(I) <- fix(I).
+relax : true <- true.
+ask: coworker(C) & myself(I) & not(I == C) <- .send(C,tell,coworker(C)).
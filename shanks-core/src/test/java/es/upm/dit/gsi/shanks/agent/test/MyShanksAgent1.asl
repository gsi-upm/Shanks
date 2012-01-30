+repair : true <- fix.
+relax : myself(I) <- .print("Nothing to do by ",I); .print(" And I am really happy ",I).
+ask: coworker(C) & myself(I) & not(I == C) <- .send(C,tell,coworker(C)); .print("Message sent by ",I, " to ",C).
+repair : true <- fix.
+relax : myself(I) <- .print("Nothing to do by ",I); .print(" And I am really happy ",I).
+coworker(C) : myself(I) & (C == I) <- fix.
  
+repair : myself(I) <- fix(I).
+relax : true <- true.
+coworker(C) : myself(I) & (C == I) <- fix(I).
  
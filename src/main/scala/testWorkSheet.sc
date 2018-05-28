
val matrice = Vector(Vector(1,2,3), Vector(4,5,6))
val rows = matrice.size
val cols = 2
var xT: Vector[Vector[Double]]= Vector.ofDim[Double](rows, cols)
matrice(1)(1)qq


(0 until rows).foreach(
  i =>
    (0 until cols).foreach(
      j =>
        xT(j)(i) = matrice(i)(j)

    )

)

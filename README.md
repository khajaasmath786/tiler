Library for dealing with tiles and coordinates
==============================================

Currently supports Mercator (Google proj) and TMS in Plate Caree [1].
The ESRI geometry library performs the heavy-lifting.

Use cases:
* Find tiles for an envelope in TMS or Mercator
* Store tiles in HBase or other k-v store with near-perfect binary distribution (little-endian keys)
* Rasterize geometry -> raster tile (for raster-vector analysis)

[1] https://en.wikipedia.org/wiki/Equirectangular_projection

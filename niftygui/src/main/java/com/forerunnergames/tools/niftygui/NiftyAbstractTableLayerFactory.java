package com.forerunnergames.tools.niftygui;

import com.forerunnergames.tools.common.Arguments;
import com.forerunnergames.tools.common.ui.TableRowPosition;

import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;

public abstract class NiftyAbstractTableLayerFactory <E extends Enum <E> & TableRowPosition<E>>
        implements NiftyTableLayerFactory
{
  private final String layerId;
  private final int topRowStartY;
  private final int deltaY;

  protected NiftyAbstractTableLayerFactory (final String layerId, final int topRowStartY, final int deltaY)
  {
    Arguments.checkIsNotNull (layerId, "layerId");

    this.layerId = layerId;
    this.topRowStartY = topRowStartY;
    this.deltaY = deltaY;
  }

  protected abstract E[] getRowPositions();
  protected abstract PanelBuilder[] createRowPanels (final E rowPosition, final int rowStartY);

  @Override
  public final LayerBuilder create()
  {
    return new LayerBuilder (layerId)
    {{
      childLayoutAbsolute();

      int rowStartY = topRowStartY;

      for (final E rowPosition : getRowPositions())
      {
        for (final PanelBuilder panelBuilder : createRowPanels (rowPosition, rowStartY))
        {
          panel (panelBuilder);
        }

        rowStartY += deltaY;
      }
    }};
  }
}

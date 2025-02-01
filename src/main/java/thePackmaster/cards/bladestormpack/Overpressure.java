package thePackmaster.cards.bladestormpack;

import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

import static thePackmaster.SpireAnniversary5Mod.makeID;
import static thePackmaster.cards.siegepack.FlavorConstants.*;

//REFS: PlotArmor (grandopening), QuickReflex (evenodd), TheMANSION (maridebuff)
public class Overpressure extends AbstractBladeStormCard {
    public final static String ID = makeID("Overpressure");
    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;
    private static final int FRAIL = 1;

    public Overpressure() {
        super(ID, COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = FRAIL;
        rawDescription = cardStrings.DESCRIPTION;
        rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        rawDescription += cardStrings.EXTENDED_DESCRIPTION[2];
        initializeDescription();

        FlavorText.AbstractCardFlavorFields.flavorBoxType.set(this, FLAVOR_BOX_TYPE);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription = cardStrings.DESCRIPTION;
        rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        rawDescription += cardStrings.EXTENDED_DESCRIPTION[2];
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty())
        {
            this.rawDescription =  cardStrings.DESCRIPTION
                    + cardStrings.EXTENDED_DESCRIPTION[0]
                    + cardStrings.EXTENDED_DESCRIPTION[1]
                    + cardStrings.EXTENDED_DESCRIPTION[2];
        }
        else
        {
            this.rawDescription =  cardStrings.DESCRIPTION
                    + cardStrings.EXTENDED_DESCRIPTION[0]
                    + cardStrings.EXTENDED_DESCRIPTION[1]
                    + makeCardTextGray(cardStrings.EXTENDED_DESCRIPTION[2]);
        }
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                //Check "First"
                if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 1) {
                    this.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
                }

                this.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
                this.addToTop(new ApplyPowerAction(p, p, new FrailPower(p, 1, false)));
                this.isDone = true;
            }
        });
        //applyToSelf(new StrengthPower(p, magicNumber));
        //applyToSelf(new LoseStrengthPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
}
